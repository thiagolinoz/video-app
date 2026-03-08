package br.com.fiap.videoapp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "aws.dynamodb.endpoint=http://localhost:8000",
        "aws.region=us-east-1",
        "aws.dynamodb.create-tables=false",
        "aws.s3.bucket=test-bucket",
        "aws.s3.endpoint=http://localhost:4566",
        "aws.s3.local-stack=false",
        "spring.kafka.producer.bootstrap-servers=localhost:9092",
        "spring.autoconfigure.exclude=" +
                "org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration," +
                "org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration"
})
class VideoAppApplicationTest {

    @Test
    @DisplayName("Contexto da aplicação deve carregar com sucesso")
    void contextLoads() {
        // verifica que o contexto Spring sobe sem erros
    }
}
