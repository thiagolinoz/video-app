package br.com.fiap.videoapp.infraestructure.event.messages;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class VideoUploadedMessageTest {

    @Test
    @DisplayName("Deve criar VideoUploadedMessage com todos os campos")
    void constructor_AllFields() {
        Date now = new Date();
        VideoUploadedMessage message = new VideoUploadedMessage(
                "user@email.com", "video-id", "RECEIVED",
                "video.mp4", "path/origin", "path/zip",
                now, null, "User Name");

        assertThat(message.nmPersonEmail()).isEqualTo("user@email.com");
        assertThat(message.idVideoSend()).isEqualTo("video-id");
        assertThat(message.cdVideoStatus()).isEqualTo("RECEIVED");
        assertThat(message.nmVideo()).isEqualTo("video.mp4");
        assertThat(message.nmVideoPathOrigin()).isEqualTo("path/origin");
        assertThat(message.nmVideoPathZip()).isEqualTo("path/zip");
        assertThat(message.dateTimeVideoCreated()).isEqualTo(now);
        assertThat(message.dateTimeVideoProcessCompleted()).isNull();
        assertThat(message.nmPersonName()).isEqualTo("User Name");
    }

    @Test
    @DisplayName("Dois records com mesmos valores devem ser iguais")
    void equality_SameValues() {
        Date now = new Date();
        VideoUploadedMessage m1 = new VideoUploadedMessage(
                "user@email.com", "video-id", "RECEIVED",
                "video.mp4", "path/origin", null, now, null, "User");
        VideoUploadedMessage m2 = new VideoUploadedMessage(
                "user@email.com", "video-id", "RECEIVED",
                "video.mp4", "path/origin", null, now, null, "User");

        assertThat(m1).isEqualTo(m2);
        assertThat(m1.hashCode()).isEqualTo(m2.hashCode());
    }

    @Test
    @DisplayName("Dois records com valores diferentes devem ser diferentes")
    void equality_DifferentValues() {
        Date now = new Date();
        VideoUploadedMessage m1 = new VideoUploadedMessage(
                "user@email.com", "video-1", "RECEIVED",
                "video.mp4", "path/1", null, now, null, "User");
        VideoUploadedMessage m2 = new VideoUploadedMessage(
                "other@email.com", "video-2", "COMPLETED",
                "other.mp4", "path/2", "path/2.zip", now, now, "Other");

        assertThat(m1).isNotEqualTo(m2);
    }

    @Test
    @DisplayName("toString deve conter os campos principais")
    void toString_ContainsFields() {
        Date now = new Date();
        VideoUploadedMessage message = new VideoUploadedMessage(
                "user@email.com", "video-id", "RECEIVED",
                "video.mp4", "path/origin", null, now, null, "User Name");

        String result = message.toString();

        assertThat(result).contains("user@email.com");
        assertThat(result).contains("video-id");
        assertThat(result).contains("RECEIVED");
        assertThat(result).contains("video.mp4");
        assertThat(result).contains("User Name");
    }

    @Test
    @DisplayName("Deve aceitar campos opcionais nulos")
    void constructor_OptionalFieldsNull() {
        VideoUploadedMessage message = new VideoUploadedMessage(
                "user@email.com", "video-id", "RECEIVED",
                "video.mp4", "path/origin", null, new Date(), null, "User Name");

        assertThat(message.nmVideoPathZip()).isNull();
        assertThat(message.dateTimeVideoProcessCompleted()).isNull();
    }
}
