package br.com.fiap.videoapp.infraestructure.web.api.dtos;

import br.com.fiap.videoapp.domain.models.VideoModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class VideoResponseDtoTest {

    private Instant now;
    private Instant completed;

    @BeforeEach
    void setUp() {
        now = Instant.parse("2026-03-01T14:00:00Z");
        completed = Instant.parse("2026-03-01T15:00:00Z");
    }

    @Test
    @DisplayName("Deve criar VideoResponseDto com todos os campos via construtor canônico")
    void canonicalConstructor_AllFields() {
        VideoResponseDto dto = new VideoResponseDto(
                "video.mp4", "video-id", "RECEIVED",
                "path/origin", "path/zip", now, completed);

        assertThat(dto.nmVideo()).isEqualTo("video.mp4");
        assertThat(dto.idVideoSend()).isEqualTo("video-id");
        assertThat(dto.cdVideoStatus()).isEqualTo("RECEIVED");
        assertThat(dto.nmVideoPathOrigin()).isEqualTo("path/origin");
        assertThat(dto.nmVideoPathZip()).isEqualTo("path/zip");
        assertThat(dto.dateTimeVideoCreated()).isEqualTo(now);
        assertThat(dto.dateTimeVideoProcessCompleted()).isEqualTo(completed);
    }

    @Test
    @DisplayName("Deve construir VideoResponseDto a partir de VideoModel")
    void constructFromVideoModel() {
        VideoModel model = new VideoModel(
                "user@email.com", "video-id", "COMPLETED",
                "video.mp4", "path/origin", "path/zip",
                now, completed, "User Name");

        VideoResponseDto dto = new VideoResponseDto(model);

        assertThat(dto.nmVideo()).isEqualTo("video.mp4");
        assertThat(dto.idVideoSend()).isEqualTo("video-id");
        assertThat(dto.cdVideoStatus()).isEqualTo("COMPLETED");
        assertThat(dto.nmVideoPathOrigin()).isEqualTo("path/origin");
        assertThat(dto.nmVideoPathZip()).isEqualTo("path/zip");
        assertThat(dto.dateTimeVideoCreated()).isEqualTo(now);
        assertThat(dto.dateTimeVideoProcessCompleted()).isEqualTo(completed);
    }

    @Test
    @DisplayName("Deve aceitar campos opcionais nulos ao construir de VideoModel")
    void constructFromVideoModel_OptionalFieldsNull() {
        VideoModel model = new VideoModel(
                "user@email.com", "video-id", "RECEIVED",
                "video.mp4", "path/origin", null,
                now, null, "User Name");

        VideoResponseDto dto = new VideoResponseDto(model);

        assertThat(dto.nmVideoPathZip()).isNull();
        assertThat(dto.dateTimeVideoProcessCompleted()).isNull();
    }

    @Test
    @DisplayName("Dois records com mesmos valores devem ser iguais")
    void equality_SameValues() {
        VideoResponseDto d1 = new VideoResponseDto(
                "video.mp4", "video-id", "RECEIVED", "path/origin", null, now, null);
        VideoResponseDto d2 = new VideoResponseDto(
                "video.mp4", "video-id", "RECEIVED", "path/origin", null, now, null);

        assertThat(d1).isEqualTo(d2);
        assertThat(d1.hashCode()).isEqualTo(d2.hashCode());
    }

    @Test
    @DisplayName("Dois records com valores diferentes devem ser diferentes")
    void equality_DifferentValues() {
        VideoResponseDto d1 = new VideoResponseDto(
                "video1.mp4", "id-1", "RECEIVED", "path/1", null, now, null);
        VideoResponseDto d2 = new VideoResponseDto(
                "video2.mp4", "id-2", "COMPLETED", "path/2", "path/2.zip", now, completed);

        assertThat(d1).isNotEqualTo(d2);
    }

    @Test
    @DisplayName("toString deve conter os campos principais")
    void toString_ContainsFields() {
        VideoResponseDto dto = new VideoResponseDto(
                "video.mp4", "video-id-123", "PROCESSING",
                "path/origin", null, now, null);

        String result = dto.toString();

        assertThat(result).contains("video.mp4");
        assertThat(result).contains("video-id-123");
        assertThat(result).contains("PROCESSING");
    }

    @Test
    @DisplayName("Deve mapear corretamente o status de cada estado")
    void constructFromVideoModel_AllStatuses() {
        for (String status : new String[]{"RECEIVED", "PROCESSING", "COMPLETED", "PROCESS_ERROR"}) {
            VideoModel model = new VideoModel(
                    "user@email.com", "video-id", status,
                    "video.mp4", "path/origin", null, now, null, "User");

            VideoResponseDto dto = new VideoResponseDto(model);

            assertThat(dto.cdVideoStatus()).isEqualTo(status);
        }
    }
}
