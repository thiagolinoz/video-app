package br.com.fiap.videoapp.domain.ports.out;

import br.com.fiap.videoapp.domain.models.PersonModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("VideoStorageRepositoryPort - Testes de contrato da interface")
class VideoStorageRepositoryPortTest {

    @Mock
    private VideoStorageRepositoryPort videoStorageRepositoryPort;

    @Mock
    private MultipartFile multipartFile;

    @Test
    @DisplayName("store deve ser chamado e retornar caminho do arquivo armazenado")
    void shouldCallStoreAndReturnFilePath() {
        PersonModel person = new PersonModel("user@test.com", "User", "pass");
        String expectedPath = "s3://bucket/videos/stored.mp4";

        when(videoStorageRepositoryPort.store(multipartFile, "stored.mp4", "/videos/", person))
                .thenReturn(expectedPath);

        String result = videoStorageRepositoryPort.store(multipartFile, "stored.mp4", "/videos/", person);

        assertThat(result).isEqualTo(expectedPath);
        verify(videoStorageRepositoryPort, times(1)).store(multipartFile, "stored.mp4", "/videos/", person);
    }

    @Test
    @DisplayName("download deve ser chamado e retornar InputStream do vídeo")
    void shouldCallDownloadAndReturnInputStream() {
        InputStream stream = new ByteArrayInputStream("video-bytes".getBytes(StandardCharsets.UTF_8));
        when(videoStorageRepositoryPort.download("/videos/video.mp4")).thenReturn(stream);

        InputStream result = videoStorageRepositoryPort.download("/videos/video.mp4");

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(stream);
        verify(videoStorageRepositoryPort, times(1)).download("/videos/video.mp4");
    }

    @Test
    @DisplayName("download deve retornar null quando o caminho não existe")
    void shouldReturnNullWhenPathNotFound() {
        when(videoStorageRepositoryPort.download("/invalid/path.mp4")).thenReturn(null);

        InputStream result = videoStorageRepositoryPort.download("/invalid/path.mp4");

        assertThat(result).isNull();
        verify(videoStorageRepositoryPort, times(1)).download("/invalid/path.mp4");
    }
}
