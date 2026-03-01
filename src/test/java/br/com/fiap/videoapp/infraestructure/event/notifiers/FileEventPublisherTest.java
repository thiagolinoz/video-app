package br.com.fiap.videoapp.infraestructure.event.notifiers;

import br.com.fiap.videoapp.domain.models.VideoModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileEventPublisherTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    private FileEventPublisher fileEventPublisher;

    private VideoModel videoModel;

    @BeforeEach
    void setUp() {
        fileEventPublisher = new FileEventPublisher(kafkaTemplate);
        videoModel = new VideoModel(
                "user@email.com", "video-id", "RECEIVED",
                "video.mp4", "path/origin", null,
                Instant.now(), null, "User Name");
    }

    @Test
    @DisplayName("Deve publicar mensagem no tópico received-videos")
    void publishNewVideo_SendsToCorrectTopic() {
        fileEventPublisher.publishNewVideo(videoModel);

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);

        verify(kafkaTemplate, times(1)).send(topicCaptor.capture(), payloadCaptor.capture());

        assertThat(topicCaptor.getValue()).isEqualTo("received-videos");
        assertThat(payloadCaptor.getValue()).contains("user@email.com");
        assertThat(payloadCaptor.getValue()).contains("video-id");
        assertThat(payloadCaptor.getValue()).contains("RECEIVED");
    }

    @Test
    @DisplayName("Deve publicar payload JSON válido no tópico received-videos")
    void publishNewVideo_PayloadIsJson() {
        fileEventPublisher.publishNewVideo(videoModel);

        ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate).send(anyString(), payloadCaptor.capture());

        String payload = payloadCaptor.getValue();
        assertThat(payload).startsWith("{");
        assertThat(payload).endsWith("}");
    }

    @Test
    @DisplayName("Deve publicar mensagem de status no tópico process-status-videos")
    void publishStatusVideo_SendsToCorrectTopic() {
        fileEventPublisher.publishStatusVideo(videoModel);

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);

        verify(kafkaTemplate, times(1)).send(topicCaptor.capture(), payloadCaptor.capture());

        assertThat(topicCaptor.getValue()).isEqualTo("process-status-videos");
        assertThat(payloadCaptor.getValue()).contains("user@email.com");
        assertThat(payloadCaptor.getValue()).contains("video-id");
        assertThat(payloadCaptor.getValue()).contains("RECEIVED");
    }

    @Test
    @DisplayName("Deve publicar payload JSON válido no tópico process-status-videos")
    void publishStatusVideo_PayloadIsJson() {
        fileEventPublisher.publishStatusVideo(videoModel);

        ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate).send(anyString(), payloadCaptor.capture());

        String payload = payloadCaptor.getValue();
        assertThat(payload).startsWith("{");
        assertThat(payload).endsWith("}");
    }

    @Test
    @DisplayName("Deve incluir nome da pessoa no payload de novo vídeo")
    void publishNewVideo_PayloadContainsPersonName() {
        fileEventPublisher.publishNewVideo(videoModel);

        ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate).send(anyString(), payloadCaptor.capture());

        assertThat(payloadCaptor.getValue()).contains("User Name");
    }

    @Test
    @DisplayName("Deve incluir nome da pessoa no payload de status")
    void publishStatusVideo_PayloadContainsPersonName() {
        fileEventPublisher.publishStatusVideo(videoModel);

        ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate).send(anyString(), payloadCaptor.capture());

        assertThat(payloadCaptor.getValue()).contains("User Name");
    }
}
