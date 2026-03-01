package br.com.fiap.videoapp.domain.services;

import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.domain.ports.out.VideoMetadaRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoMetadataServiceTest {

    @Mock
    private VideoMetadaRepositoryPort videoMetadaRepositoryPort;

    @InjectMocks
    private VideoMetadataService videoMetadataService;

    @Test
    @DisplayName("Deve retornar lista de vídeos do usuário")
    void listVideos_ReturnsVideoList() {
        VideoModel video1 = new VideoModel("user@email.com", "id-1", "RECEIVED",
                "video1.mp4", "path/1", null, Instant.now(), null, "User");
        VideoModel video2 = new VideoModel("user@email.com", "id-2", "COMPLETED",
                "video2.mp4", "path/2", "path/2.zip", Instant.now(), Instant.now(), "User");

        when(videoMetadaRepositoryPort.listVideos("user@email.com")).thenReturn(List.of(video1, video2));

        List<VideoModel> result = videoMetadataService.listVideos("user@email.com");

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getIdVideoSend()).isEqualTo("id-1");
        assertThat(result.get(1).getIdVideoSend()).isEqualTo("id-2");

        verify(videoMetadaRepositoryPort, times(1)).listVideos("user@email.com");
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando usuário não tem vídeos")
    void listVideos_ReturnsEmptyList() {
        when(videoMetadaRepositoryPort.listVideos("empty@email.com")).thenReturn(List.of());

        List<VideoModel> result = videoMetadataService.listVideos("empty@email.com");

        assertThat(result).isEmpty();
        verify(videoMetadaRepositoryPort, times(1)).listVideos("empty@email.com");
    }
}
