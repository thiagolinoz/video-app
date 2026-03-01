package br.com.fiap.videoapp.infraestructure.event.messages;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VideoUpdateStatusMessageTest {

    @Test
    @DisplayName("Deve criar VideoUpdateStatusMessage com todos os campos")
    void constructor_AllFields() {
        VideoUpdateStatusMessage message = new VideoUpdateStatusMessage(
                "user@email.com", "video-id", "COMPLETED", "video.mp4", "User Name");

        assertThat(message.nmPersonEmail()).isEqualTo("user@email.com");
        assertThat(message.idVideoSend()).isEqualTo("video-id");
        assertThat(message.cdVideoStatus()).isEqualTo("COMPLETED");
        assertThat(message.nmVideo()).isEqualTo("video.mp4");
        assertThat(message.nmPersonName()).isEqualTo("User Name");
    }

    @Test
    @DisplayName("Dois records com mesmos valores devem ser iguais")
    void equality_SameValues() {
        VideoUpdateStatusMessage m1 = new VideoUpdateStatusMessage(
                "user@email.com", "video-id", "RECEIVED", "video.mp4", "User");
        VideoUpdateStatusMessage m2 = new VideoUpdateStatusMessage(
                "user@email.com", "video-id", "RECEIVED", "video.mp4", "User");

        assertThat(m1).isEqualTo(m2);
        assertThat(m1.hashCode()).isEqualTo(m2.hashCode());
    }

    @Test
    @DisplayName("Dois records com valores diferentes devem ser diferentes")
    void equality_DifferentValues() {
        VideoUpdateStatusMessage m1 = new VideoUpdateStatusMessage(
                "user@email.com", "video-1", "RECEIVED", "video.mp4", "User");
        VideoUpdateStatusMessage m2 = new VideoUpdateStatusMessage(
                "other@email.com", "video-2", "COMPLETED", "other.mp4", "Other");

        assertThat(m1).isNotEqualTo(m2);
    }

    @Test
    @DisplayName("toString deve conter os campos principais")
    void toString_ContainsFields() {
        VideoUpdateStatusMessage message = new VideoUpdateStatusMessage(
                "user@email.com", "video-id", "PROCESSING", "video.mp4", "User Name");

        String result = message.toString();

        assertThat(result).contains("user@email.com");
        assertThat(result).contains("video-id");
        assertThat(result).contains("PROCESSING");
        assertThat(result).contains("video.mp4");
        assertThat(result).contains("User Name");
    }

    @Test
    @DisplayName("Deve aceitar valores nulos nos campos")
    void constructor_NullValues() {
        VideoUpdateStatusMessage message = new VideoUpdateStatusMessage(null, null, null, null, null);

        assertThat(message.nmPersonEmail()).isNull();
        assertThat(message.idVideoSend()).isNull();
        assertThat(message.cdVideoStatus()).isNull();
        assertThat(message.nmVideo()).isNull();
        assertThat(message.nmPersonName()).isNull();
    }
}
