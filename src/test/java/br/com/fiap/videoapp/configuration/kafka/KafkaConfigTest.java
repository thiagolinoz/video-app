package br.com.fiap.videoapp.configuration.kafka;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("KafkaConfig - Testes unitários")
class KafkaConfigTest {

    private final KafkaConfig kafkaConfig = new KafkaConfig();

    @Test
    @DisplayName("producerFactory() deve retornar instância não nula de ProducerFactory")
    void shouldCreateProducerFactory() {
        ProducerFactory<String, String> factory = kafkaConfig.producerFactory();

        assertThat(factory).isNotNull();
    }

    @Test
    @DisplayName("kafkaTemplate() deve retornar instância não nula de KafkaTemplate")
    void shouldCreateKafkaTemplate() {
        KafkaTemplate<String, String> template = kafkaConfig.kafkaTemplate();

        assertThat(template).isNotNull();
    }

    @Test
    @DisplayName("Chamadas sucessivas a producerFactory() devem retornar instâncias independentes")
    void shouldReturnIndependentProducerFactoryInstances() {
        ProducerFactory<String, String> first = kafkaConfig.producerFactory();
        ProducerFactory<String, String> second = kafkaConfig.producerFactory();

        assertThat(first).isNotNull();
        assertThat(second).isNotNull();
        assertThat(first).isNotSameAs(second);
    }
}
