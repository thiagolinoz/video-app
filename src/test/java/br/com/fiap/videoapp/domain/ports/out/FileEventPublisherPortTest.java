package br.com.fiap.videoapp.domain.ports.out;

import br.com.fiap.videoapp.domain.models.VideoModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FileEventPublisherPort - Testes de contrato da interface")
class FileEventPublisherPortTest {

    @Mock
    private FileEventPublisherPort fileEventPublisherPort;

    private VideoModel buildVideoModel(String status) {
        return new VideoModel(
                "user@test.com", "vid-1", status,
                "video.mp4", "/origin", "/zip",
                Instant.now(), null, "User"
        );
    }

    @Test
    @DisplayName("publishNewVideo deve ser chamado com VideoModel")
    void shouldCallPublishNewVideo() {
        VideoModel video = buildVideoModel("RECEIVED");
        doNothing().when(fileEventPublisherPort).publishNewVideo(video);

        fileEventPublisherPort.publishNewVideo(video);

        verify(fileEventPublisherPort, times(1)).publishNewVideo(video);
    }

    @Test
    @DisplayName("publishStatusVideo deve ser chamado com VideoModel")
    void shouldCallPublishStatusVideo() {
        VideoModel video = buildVideoModel("PROCESSING");
        doNothing().when(fileEventPublisherPort).publishStatusVideo(video);

        fileEventPublisherPort.publishStatusVideo(video);

        verify(fileEventPublisherPort, times(1)).publishStatusVideo(video);
    }

    @Test
    @DisplayName("publishNewVideo e publishStatusVideo devem ser chamados de forma independente")
    void shouldCallBothPublishMethodsIndependently() {
        VideoModel newVideo = buildVideoModel("RECEIVED");
        VideoModel statusVideo = buildVideoModel("COMPLETED");

        doNothing().when(fileEventPublisherPort).publishNewVideo(newVideo);
        doNothing().when(fileEventPublisherPort).publishStatusVideo(statusVideo);

        fileEventPublisherPort.publishNewVideo(newVideo);
        fileEventPublisherPort.publishStatusVideo(statusVideo);

        verify(fileEventPublisherPort, times(1)).publishNewVideo(newVideo);
        verify(fileEventPublisherPort, times(1)).publishStatusVideo(statusVideo);
        verifyNoMoreInteractions(fileEventPublisherPort);
    }
}
