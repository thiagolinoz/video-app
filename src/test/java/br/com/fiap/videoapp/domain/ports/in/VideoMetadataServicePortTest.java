package br.com.fiap.videoapp.domain.ports.in;

import br.com.fiap.videoapp.domain.models.VideoModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("VideoMetadataServicePort - Testes de contrato da interface")
class VideoMetadataServicePortTest {

    @Mock
    private VideoMetadataServicePort videoMetadataServicePort;

    @Test
    @DisplayName("listVideos deve ser chamado com e-mail e retornar lista de VideoModel")
    void shouldCallListVideosAndReturnList() {
        VideoModel video = new VideoModel(
                "user@test.com", "vid-1", "COMPLETED",
                "video.mp4", "/origin", "/zip",
                Instant.now(), Instant.now(), "User"
        );
        when(videoMetadataServicePort.listVideos("user@test.com")).thenReturn(List.of(video));

        List<VideoModel> result = videoMetadataServicePort.listVideos("user@test.com");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNmPersonEmail()).isEqualTo("user@test.com");
        verify(videoMetadataServicePort, times(1)).listVideos("user@test.com");
    }

    @Test
    @DisplayName("listVideos deve retornar lista vazia quando não há vídeos")
    void shouldReturnEmptyListWhenNoVideos() {
        when(videoMetadataServicePort.listVideos("empty@test.com")).thenReturn(List.of());

        List<VideoModel> result = videoMetadataServicePort.listVideos("empty@test.com");

        assertThat(result).isEmpty();
        verify(videoMetadataServicePort, times(1)).listVideos("empty@test.com");
    }
}
