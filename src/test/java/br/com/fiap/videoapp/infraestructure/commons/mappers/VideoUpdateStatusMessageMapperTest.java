package br.com.fiap.videoapp.infraestructure.commons.mappers;

import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.infraestructure.event.messages.VideoUpdateStatusMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class VideoUpdateStatusMessageMapperTest {

    private VideoModel videoModel;

    @BeforeEach
    void setUp() {
        videoModel = new VideoModel("user@email.com", "video-xyz", "COMPLETED",
                "video.mp4", "videos/user@email.com/video.mp4", "videos/user@email.com/video.zip",
                Instant.now(), Instant.now(), "User Name");
    }

    @Test
    @DisplayName("Deve mapear VideoModel para VideoUpdateStatusMessage corretamente")
    void toMessage_Success() {
        VideoUpdateStatusMessage result = VideoUpdateStatusMessageMapper.toMessage(videoModel);

        assertThat(result).isNotNull();
        assertThat(result.nmPersonEmail()).isEqualTo("user@email.com");
        assertThat(result.idVideoSend()).isEqualTo("video-xyz");
        assertThat(result.cdVideoStatus()).isEqualTo("COMPLETED");
        assertThat(result.nmVideo()).isEqualTo("video.mp4");
        assertThat(result.nmPersonName()).isEqualTo("User Name");
    }

    @Test
    @DisplayName("Deve mapear status RECEIVED corretamente")
    void toMessage_StatusReceived() {
        videoModel.setCdVideoStatus("RECEIVED");

        VideoUpdateStatusMessage result = VideoUpdateStatusMessageMapper.toMessage(videoModel);

        assertThat(result.cdVideoStatus()).isEqualTo("RECEIVED");
    }

    @Test
    @DisplayName("Deve mapear status PROCESS_ERROR corretamente")
    void toMessage_StatusError() {
        videoModel.setCdVideoStatus("PROCESS_ERROR");

        VideoUpdateStatusMessage result = VideoUpdateStatusMessageMapper.toMessage(videoModel);

        assertThat(result.cdVideoStatus()).isEqualTo("PROCESS_ERROR");
    }
}
