package br.com.fiap.videoapp.infraestructure.commons.mappers;

import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.infraestructure.event.messages.VideoUploadedMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class VideoUploadedMapperTest {

    private VideoModel videoModel;
    private Instant now;

    @BeforeEach
    void setUp() {
        now = Instant.now();
        videoModel = new VideoModel("user@email.com", "video-abc", "RECEIVED",
                "video.mp4", "videos/user@email.com/video.mp4", null,
                now, null, "User Name");
    }

    @Test
    @DisplayName("Deve mapear VideoModel para VideoUploadedMessage corretamente")
    void toVideoUploadedModel_Success() {
        VideoUploadedMessage result = VideoUploadedMapper.toVideoUploadedModel(videoModel);

        assertThat(result).isNotNull();
        assertThat(result.nmPersonEmail()).isEqualTo("user@email.com");
        assertThat(result.idVideoSend()).isEqualTo("video-abc");
        assertThat(result.cdVideoStatus()).isEqualTo("RECEIVED");
        assertThat(result.nmVideo()).isEqualTo("video.mp4");
        assertThat(result.nmVideoPathOrigin()).isEqualTo("videos/user@email.com/video.mp4");
        assertThat(result.nmPersonName()).isEqualTo("User Name");
    }

    @Test
    @DisplayName("Deve definir nmVideoPathZip como nulo na mensagem")
    void toVideoUploadedModel_ZipPathIsNull() {
        VideoUploadedMessage result = VideoUploadedMapper.toVideoUploadedModel(videoModel);

        assertThat(result.nmVideoPathZip()).isNull();
    }

    @Test
    @DisplayName("Deve definir dateTimeVideoProcessCompleted como nulo na mensagem")
    void toVideoUploadedModel_ProcessCompletedIsNull() {
        VideoUploadedMessage result = VideoUploadedMapper.toVideoUploadedModel(videoModel);

        assertThat(result.dateTimeVideoProcessCompleted()).isNull();
    }

    @Test
    @DisplayName("Deve converter Instant para Date corretamente")
    void toVideoUploadedModel_DateConvertedCorrectly() {
        VideoUploadedMessage result = VideoUploadedMapper.toVideoUploadedModel(videoModel);

        assertThat(result.dateTimeVideoCreated()).isNotNull();
        // Date tem precisão de milissegundos; truncar nanosegundos antes de comparar
        assertThat(result.dateTimeVideoCreated().getTime()).isEqualTo(now.toEpochMilli());
    }
}
