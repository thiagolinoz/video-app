package br.com.fiap.videoapp.domain.ports.in;

import br.com.fiap.videoapp.domain.models.VideoDownloadModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("VideoStorageServicePort - Testes de contrato da interface")
class VideoStorageServicePortTest {

    @Mock
    private VideoStorageServicePort videoStorageServicePort;

    @Mock
    private MultipartFile multipartFile;

    @Test
    @DisplayName("store deve ser chamado com arquivo e e-mail sem lançar exceção")
    void shouldCallStoreWithFileAndEmail() {
        doNothing().when(videoStorageServicePort).store(multipartFile, "user@test.com");

        videoStorageServicePort.store(multipartFile, "user@test.com");

        verify(videoStorageServicePort, times(1)).store(multipartFile, "user@test.com");
    }

    @Test
    @DisplayName("downloadVideo deve retornar VideoDownloadModel com nome e stream")
    void shouldCallDownloadVideoAndReturnModel() {
        VideoDownloadModel downloadModel = new VideoDownloadModel(
                "video.mp4",
                new ByteArrayInputStream("bytes".getBytes(StandardCharsets.UTF_8))
        );
        when(videoStorageServicePort.downloadVideo("user@test.com", "vid-001")).thenReturn(downloadModel);

        VideoDownloadModel result = videoStorageServicePort.downloadVideo("user@test.com", "vid-001");

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("video.mp4");
        assertThat(result.getVideo()).isNotNull();
        verify(videoStorageServicePort, times(1)).downloadVideo("user@test.com", "vid-001");
    }
}
