# Video App

API REST para **upload, download e gerenciamento de vídeos** desenvolvida com arquitetura hexagonal (Ports & Adapters). Após o upload, os metadados são persistidos no DynamoDB e eventos são publicados no Kafka para que serviços externos realizem o processamento dos vídeos (compressão, geração de zip, etc.).

---

## Sumário

- [Tecnologias](#tecnologias)
- [Arquitetura](#arquitetura)
- [Endpoints da API](#endpoints-da-api)
- [Kafka — Tópicos e Eventos](#kafka--tópicos-e-eventos)
- [AWS S3 — Armazenamento](#aws-s3--armazenamento)
- [AWS DynamoDB — Tabelas](#aws-dynamodb--tabelas)
- [Configuração e Variáveis de Ambiente](#configuração-e-variáveis-de-ambiente)
- [Como Executar Localmente](#como-executar-localmente)
- [Build e Empacotamento](#build-e-empacotamento)
- [Deploy com Docker](#deploy-com-docker)
- [Deploy no Kubernetes](#deploy-no-kubernetes)
- [Fluxo Principal de Upload](#fluxo-principal-de-upload)
- [Testes](#testes)

---

## Tecnologias

| Categoria | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot 3.4.5 |
| Build | Maven |
| Armazenamento de objetos | AWS S3 (S3 Transfer Manager + S3AsyncClient CRT) |
| Banco de dados | AWS DynamoDB (SDK v2 Enhanced) |
| Mensageria | Apache Kafka (`spring-kafka`) |
| Documentação | SpringDoc OpenAPI 2.8.8 (Swagger UI) |
| Mapeamento/Boilerplate | Lombok |
| Validação | Spring Validation (Jakarta) |
| S3 local (dev) | LocalStack |
| DynamoDB local (dev) | `amazon/dynamodb-local` + `aaronshaf/dynamodb-admin` |
| Container base | Amazon Corretto 21 (Amazon Linux 2023) |

---

## Arquitetura

O diagrama completo da arquitetura está disponível em [`arquiteruraProjeto/final_challenge.drawio`](arquiteruraProjeto/final_challenge.drawio).

O projeto segue o padrão **Hexagonal Architecture (Ports & Adapters)**:

```
┌──────────────────────────────────────────────────────────────────┐
│                           DOMAIN                                 │
│                                                                  │
│  Models: VideoModel, PersonModel, VideoDownloadModel             │
│  Enums:  VideoStatusEnum (RECEIVED, PROCESSING, COMPLETED,       │
│          PROCESS_ERROR)                                          │
│                                                                  │
│  Ports IN (interfaces de entrada):                               │
│    PersonServicePort       → PersonService                       │
│    VideoStorageServicePort → VideoStorageService                 │
│    VideoMetadataServicePort → VideoMetadataService               │
│                                                                  │
│  Ports OUT (interfaces de saída):                                │
│    PersonRepositoryPort       → PersonRepository (DynamoDB)      │
│    VideoMetadaRepositoryPort  → VideoMetadaRepository (DynamoDB) │
│    VideoStorageRepositoryPort → VideoStorageRepository (S3)      │
│    FileEventPublisherPort     → FileEventPublisher (Kafka)       │
└──────────────────────────────────────────────────────────────────┘
         ↑                                          ↑
┌─────────────────────┐              ┌──────────────────────────┐
│  WEB (Controllers)  │              │     INFRASTRUCTURE        │
│  PersonController   │              │  Repositórios DynamoDB    │
│  VideoController    │              │  Repositório S3           │
└─────────────────────┘              │  Publisher Kafka          │
                                     └──────────────────────────┘
```

---

## Endpoints da API

Base path: `/api/v1`  
Autenticação: **HTTP Basic Auth** (credenciais validadas contra o DynamoDB).

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/v1/user/new` | Cadastra novo usuário |
| `GET` | `/api/v1/user/{email}/videos` | Lista os vídeos do usuário |
| `POST` | `/api/v1/user/{email}/videos/upload` | Faz upload de vídeo (multipart/form-data) |
| `GET` | `/api/v1/user/{email}/videos/{idVideo}/download` | Faz download do vídeo original |

> **Limite de upload:** 500 MB por arquivo/requisição.

A documentação interativa (Swagger UI) fica disponível em:  
`http://localhost:8080/swagger-ui/index.html`

---

## Kafka — Tópicos e Eventos

Broker configurado em `kafka.video.internal:9092` (interno ao cluster Kubernetes).

| Tópico | Payload | Quando é publicado |
|---|---|---|
| `received-videos` | `VideoUploadedMessage` (JSON) | Imediatamente após upload bem-sucedido no S3 |
| `process-status-videos` | `VideoUpdateStatusMessage` (JSON) | Após upload, para notificar o status atual do vídeo |

Os payloads incluem: email do usuário, ID do vídeo (UUID), status, nome do arquivo, caminho no S3 (original e zip), timestamps de criação e conclusão.

---

## AWS S3 — Armazenamento

| Ambiente | Bucket | Endpoint |
|---|---|---|
| Produção | `postech-fiap-bucket-videos` | AWS padrão |
| Local (LocalStack) | `bucket-videos` | `http://localhost:4566` |

- **Chave dos objetos:** `videos/{email}/{uuid}_{filename}`  
  (nome normalizado: lowercase, sem acentos, espaços substituídos por `-`)
- Upload via **S3 Transfer Manager** (assíncrono)
- Download via `S3Client` síncrono

---

## AWS DynamoDB — Tabelas

| Tabela | Partition Key | Sort Key | GSI |
|---|---|---|---|
| `Persons` | `nmEmail` (String) | — | — |
| `Videos` | `nmPessoaEmail` (String) | `idVideoSend` (String/UUID) | `cdVideoStatusIndex` (por status) |

A criação automática de tabelas pode ser ativada via `aws.dynamodb.create-tables=true` (desabilitado em produção).

---

## Configuração e Variáveis de Ambiente

| Propriedade | Descrição | Padrão/Exemplo |
|---|---|---|
| `AWS_REGION` | Região AWS | `us-east-1` |
| `AWS_ACCESS_KEY_ID` | Access key AWS | — |
| `AWS_SECRET_ACCESS_KEY` | Secret key AWS | — |
| `AWS_SESSION_TOKEN` | Session token AWS (Academy) | — |
| `aws.s3.local-stack` | Ativa modo LocalStack para S3 | `false` |
| `aws.s3.bucket-name` | Nome do bucket S3 | `postech-fiap-bucket-videos` |
| `aws.dynamodb.endpoint` | Endpoint do DynamoDB local | `http://localhost:8000` |
| `aws.dynamodb.create-tables` | Cria tabelas automaticamente | `false` |
| `spring.kafka.bootstrap-servers` | Endereço do broker Kafka | `kafka.video.internal:9092` |

---

## Como Executar Localmente

### Pré-requisitos

- Java 21+
- Maven 3.8+
- Docker e Docker Compose

### 1. Subir a infraestrutura local

```bash
docker compose up -d
```

Isso inicia:
- **DynamoDB local** → `http://localhost:8000`
- **DynamoDB Admin UI** → `http://localhost:8001`
- **LocalStack (S3)** → `http://localhost:4566` (bucket `bucket-videos` criado automaticamente)

### 2. Executar a aplicação

```bash
./mvnw spring-boot:run
```

Ou, após gerar o JAR:

```bash
java -jar target/video-app.jar
```

---

## Build e Empacotamento

```bash
# Compilar e empacotar (sem rodar os testes)
./mvnw clean package -DskipTests

# Compilar e empacotar (com testes)
./mvnw clean package
```

O artefato gerado será `target/video-app.jar`.

---

## Deploy com Docker

```bash
# Build da imagem
docker build -t video-app .

# Executar o container
docker run -p 8080:8080 \
  -e AWS_REGION=us-east-1 \
  -e AWS_ACCESS_KEY_ID=<sua-access-key> \
  -e AWS_SECRET_ACCESS_KEY=<sua-secret-key> \
  -e AWS_SESSION_TOKEN=<seu-session-token> \
  video-app
```

> O container roda com um usuário dedicado `video-app` (não-root) por segurança.

---

## Deploy no Kubernetes

Os manifests estão em `k8s/`:

```bash
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/application/application-deployment.yaml
kubectl apply -f k8s/application/application-service.yaml
kubectl apply -f k8s/application/application-hpa.yaml
```

### Configurações do cluster

| Recurso | Configuração |
|---|---|
| **Deployment** | 1 réplica inicial; CPU 100m–500m; RAM 128Mi–256Mi; porta 8080 |
| **Service** | `LoadBalancer`; porta `80` → `8080` |
| **HPA** | Mín. 2 réplicas / Máx. 5; escala quando CPU média > 25% |

As credenciais AWS são fornecidas via Secret `aws-academy-credentials` (campos: `access-key-id`, `secret-access-key`, `session-token`).

---

## Fluxo Principal de Upload

```
Cliente
  │
  ├─► POST /api/v1/user/{email}/videos/upload  (Basic Auth + multipart)
  │
  └─► VideoController
        │  valida autenticação (DynamoDB → tabela Persons)
        │
        └─► VideoStorageService.store()
              ├── gera UUID para o vídeo
              ├── upload no S3  (videos/{email}/{uuid}_{filename})
              ├── salva metadados no DynamoDB (status: RECEIVED)
              ├── publica em Kafka → tópico "received-videos"
              └── publica em Kafka → tópico "process-status-videos"
                        │
                        └─► Serviço externo de processamento
```

---

## Testes

```bash
# Executar todos os testes
./mvnw test

# Gerar relatório de testes (Surefire)
./mvnw surefire-report:report
```

Os relatórios são gerados em `target/surefire-reports/`.
