package br.com.fiap.videoapp.domain.services;

import br.com.fiap.videoapp.domain.models.PersonModel;
import br.com.fiap.videoapp.domain.models.VideoDownloadModel;
import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.domain.ports.out.FileEventPublisherPort;
import br.com.fiap.videoapp.domain.ports.out.PersonRepositoryPort;
import br.com.fiap.videoapp.domain.ports.out.VideoMetadaRepositoryPort;
import br.com.fiap.videoapp.domain.ports.out.VideoStorageRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoStorageServiceTest {

    @Mock
    private VideoMetadaRepositoryPort videoMetadaRepositoryPort;

    @Mock
    private VideoStorageRepositoryPort videoStorageRepositoryPort;

    @Mock
    private FileEventPublisherPort fileEventPublisherPort;

    @Mock
    private PersonRepositoryPort personRepositoryPort;

    @InjectMocks
    private VideoStorageService videoStorageService;

    private PersonModel personModel;
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        personModel = new PersonModel("user@email.com", "User Name", "password");
        multipartFile = new MockMultipartFile("file", "test-video.mp4", "video/mp4", "video-content".getBytes());
    }

    @Test
    @DisplayName("Deve fazer upload do vídeo com sucesso")
    void store_Success() {
        when(personRepositoryPort.getPersonByEmail("user@email.com")).thenReturn(Optional.of(personModel));
        when(videoStorageRepositoryPort.store(any(), anyString(), anyString(), any(PersonModel.class)))
                .thenReturn("videos/user@email.com/test-video.mp4");
        when(videoMetadaRepositoryPort.save(any(VideoModel.class))).thenReturn(new VideoModel());
        doNothing().when(fileEventPublisherPort).publishNewVideo(any(VideoModel.class));
        doNothing().when(fileEventPublisherPort).publishStatusVideo(any(VideoModel.class));

        videoStorageService.store(multipartFile, "user@email.com");

        verify(personRepositoryPort, times(1)).getPersonByEmail("user@email.com");
        verify(videoStorageRepositoryPort, times(1)).store(any(), anyString(), eq("videos"), eq(personModel));
        verify(videoMetadaRepositoryPort, times(1)).save(any(VideoModel.class));
        verify(fileEventPublisherPort, times(1)).publishNewVideo(any(VideoModel.class));
        verify(fileEventPublisherPort, times(1)).publishStatusVideo(any(VideoModel.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando pessoa não existe no store")
    void store_PersonNotFound_ThrowsException() {
        when(personRepositoryPort.getPersonByEmail("unknown@email.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> videoStorageService.store(multipartFile, "unknown@email.com"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("This person does not exists");

        verify(videoStorageRepositoryPort, never()).store(any(), any(), any(), any());
        verify(videoMetadaRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Deve fazer download do vídeo com sucesso")
    void downloadVideo_Success() {
        VideoModel videoModel = new VideoModel("user@email.com", "video-id", "COMPLETED",
                "video.mp4", "videos/user@email.com/video.mp4", "videos/user@email.com/video.zip",
                Instant.now(), Instant.now(), "User Name");

        InputStream inputStream = new ByteArrayInputStream("zip-content".getBytes());

        when(videoMetadaRepositoryPort.findBy("user@email.com", "video-id")).thenReturn(Optional.of(videoModel));
        when(videoStorageRepositoryPort.download("videos/user@email.com/video.zip")).thenReturn(inputStream);

        VideoDownloadModel result = videoStorageService.downloadVideo("user@email.com", "video-id");

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("video.zip");
        assertThat(result.getVideo()).isEqualTo(inputStream);
    }

    @Test
    @DisplayName("Deve lançar exceção quando vídeo não encontrado no download")
    void downloadVideo_NotFound_ThrowsException() {
        when(videoMetadaRepositoryPort.findBy("user@email.com", "nonexistent-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> videoStorageService.downloadVideo("user@email.com", "nonexistent-id"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("This person dont have access to this video");

        verify(videoStorageRepositoryPort, never()).download(any());
    }
}
