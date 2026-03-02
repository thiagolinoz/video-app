package br.com.fiap.videoapp.domain.ports.out;

import br.com.fiap.videoapp.domain.models.VideoModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("VideoMetadaRepositoryPort - Testes de contrato da interface")
class VideoMetadaRepositoryPortTest {

    @Mock
    private VideoMetadaRepositoryPort videoMetadaRepositoryPort;

    private VideoModel buildVideo(String email, String id, String status) {
        return new VideoModel(email, id, status, "video.mp4", "/origin", "/zip",
                Instant.now(), null, "User");
    }

    @Test
    @DisplayName("listVideos deve retornar lista de VideoModel para o e-mail informado")
    void shouldReturnListOfVideos() {
        VideoModel video = buildVideo("user@test.com", "vid-1", "RECEIVED");
        when(videoMetadaRepositoryPort.listVideos("user@test.com")).thenReturn(List.of(video));

        List<VideoModel> result = videoMetadaRepositoryPort.listVideos("user@test.com");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getIdVideoSend()).isEqualTo("vid-1");
        verify(videoMetadaRepositoryPort, times(1)).listVideos("user@test.com");
    }

    @Test
    @DisplayName("listVideos deve retornar lista vazia quando não há vídeos")
    void shouldReturnEmptyListWhenNoVideos() {
        when(videoMetadaRepositoryPort.listVideos("empty@test.com")).thenReturn(List.of());

        List<VideoModel> result = videoMetadaRepositoryPort.listVideos("empty@test.com");

        assertThat(result).isEmpty();
        verify(videoMetadaRepositoryPort, times(1)).listVideos("empty@test.com");
    }

    @Test
    @DisplayName("save deve persistir e retornar o VideoModel")
    void shouldSaveAndReturnVideoModel() {
        VideoModel video = buildVideo("user@test.com", "vid-2", "PROCESSING");
        when(videoMetadaRepositoryPort.save(video)).thenReturn(video);

        VideoModel result = videoMetadaRepositoryPort.save(video);

        assertThat(result).isNotNull();
        assertThat(result.getIdVideoSend()).isEqualTo("vid-2");
        verify(videoMetadaRepositoryPort, times(1)).save(video);
    }

    @Test
    @DisplayName("findBy deve retornar Optional com VideoModel quando encontrado")
    void shouldReturnOptionalPresentWhenFound() {
        VideoModel video = buildVideo("user@test.com", "vid-3", "COMPLETED");
        when(videoMetadaRepositoryPort.findBy("user@test.com", "vid-3")).thenReturn(Optional.of(video));

        Optional<VideoModel> result = videoMetadaRepositoryPort.findBy("user@test.com", "vid-3");

        assertThat(result).isPresent();
        assertThat(result.get().getCdVideoStatus()).isEqualTo("COMPLETED");
        verify(videoMetadaRepositoryPort, times(1)).findBy("user@test.com", "vid-3");
    }

    @Test
    @DisplayName("findBy deve retornar Optional vazio quando não encontrado")
    void shouldReturnEmptyOptionalWhenNotFound() {
        when(videoMetadaRepositoryPort.findBy("user@test.com", "vid-999")).thenReturn(Optional.empty());

        Optional<VideoModel> result = videoMetadaRepositoryPort.findBy("user@test.com", "vid-999");

        assertThat(result).isEmpty();
        verify(videoMetadaRepositoryPort, times(1)).findBy("user@test.com", "vid-999");
    }
}
