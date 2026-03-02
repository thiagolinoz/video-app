package br.com.fiap.videoapp.domain.models;

import br.com.fiap.videoapp.domain.enums.VideoStatusEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("VideoModel - Testes unitários")
class VideoModelTest {

    @Test
    @DisplayName("Deve criar VideoModel com construtor padrão e setters")
    void shouldCreateWithDefaultConstructorAndSetters() {
        Instant now = Instant.now();
        VideoModel video = new VideoModel();
        video.setNmPersonEmail("user@test.com");
        video.setIdVideoSend("video-001");
        video.setCdVideoStatus(VideoStatusEnum.RECEIVED.name());
        video.setNmVideo("meu-video.mp4");
        video.setNmVideoPathOrigin("/videos/meu-video.mp4");
        video.setNmVideoPathZip("/videos/meu-video.zip");
        video.setDateTimeVideoCreated(now);
        video.setDateTimeVideoProcessCompleted(now);
        video.setNmPersonName("User Test");

        assertThat(video.getNmPersonEmail()).isEqualTo("user@test.com");
        assertThat(video.getIdVideoSend()).isEqualTo("video-001");
        assertThat(video.getCdVideoStatus()).isEqualTo("RECEIVED");
        assertThat(video.getNmVideo()).isEqualTo("meu-video.mp4");
        assertThat(video.getNmVideoPathOrigin()).isEqualTo("/videos/meu-video.mp4");
        assertThat(video.getNmVideoPathZip()).isEqualTo("/videos/meu-video.zip");
        assertThat(video.getDateTimeVideoCreated()).isEqualTo(now);
        assertThat(video.getDateTimeVideoProcessCompleted()).isEqualTo(now);
        assertThat(video.getNmPersonName()).isEqualTo("User Test");
    }

    @Test
    @DisplayName("Deve criar VideoModel com construtor parametrizado")
    void shouldCreateWithParameterizedConstructor() {
        Instant created = Instant.parse("2026-01-01T10:00:00Z");
        Instant completed = Instant.parse("2026-01-01T11:00:00Z");

        VideoModel video = new VideoModel(
                "user@test.com", "vid-123", "PROCESSING",
                "video.mp4", "/origin/path", "/zip/path",
                created, completed, "Test User"
        );

        assertThat(video.getNmPersonEmail()).isEqualTo("user@test.com");
        assertThat(video.getIdVideoSend()).isEqualTo("vid-123");
        assertThat(video.getCdVideoStatus()).isEqualTo("PROCESSING");
        assertThat(video.getNmVideo()).isEqualTo("video.mp4");
        assertThat(video.getNmVideoPathOrigin()).isEqualTo("/origin/path");
        assertThat(video.getNmVideoPathZip()).isEqualTo("/zip/path");
        assertThat(video.getDateTimeVideoCreated()).isEqualTo(created);
        assertThat(video.getDateTimeVideoProcessCompleted()).isEqualTo(completed);
        assertThat(video.getNmPersonName()).isEqualTo("Test User");
    }

    @Test
    @DisplayName("buildVideoModel deve preencher os campos corretamente a partir de PersonModel")
    void shouldBuildVideoModelFromPersonModel() {
        PersonModel person = new PersonModel("person@test.com", "Person Name", "password");
        VideoModel video = new VideoModel();

        VideoModel result = video.buildVideoModel(
                "vid-999", "saved.mp4", "/path/saved.mp4", VideoStatusEnum.RECEIVED, person
        );

        assertThat(result.getNmPersonEmail()).isEqualTo("person@test.com");
        assertThat(result.getIdVideoSend()).isEqualTo("vid-999");
        assertThat(result.getCdVideoStatus()).isEqualTo("RECEIVED");
        assertThat(result.getNmVideo()).isEqualTo("saved.mp4");
        assertThat(result.getNmVideoPathOrigin()).isEqualTo("/path/saved.mp4");
        assertThat(result.getNmVideoPathZip()).isNull();
        assertThat(result.getDateTimeVideoCreated()).isNotNull();
        assertThat(result.getDateTimeVideoProcessCompleted()).isNull();
        assertThat(result.getNmPersonName()).isEqualTo("Person Name");
    }

    @Test
    @DisplayName("toString deve conter os principais campos do VideoModel")
    void shouldContainFieldsInToString() {
        VideoModel video = new VideoModel(
                "user@test.com", "vid-001", "COMPLETED",
                "video.mp4", "/origin", "/zip",
                Instant.parse("2026-01-01T10:00:00Z"), null, "User"
        );

        String result = video.toString();

        assertThat(result).contains("user@test.com");
        assertThat(result).contains("vid-001");
        assertThat(result).contains("COMPLETED");
        assertThat(result).contains("video.mp4");
        assertThat(result).contains("/origin");
        assertThat(result).contains("User");
    }

    @Test
    @DisplayName("Deve retornar null para campos não definidos no construtor padrão")
    void shouldReturnNullForUninitializedFields() {
        VideoModel video = new VideoModel();

        assertThat(video.getNmPersonEmail()).isNull();
        assertThat(video.getIdVideoSend()).isNull();
        assertThat(video.getCdVideoStatus()).isNull();
        assertThat(video.getNmVideo()).isNull();
        assertThat(video.getNmVideoPathOrigin()).isNull();
        assertThat(video.getNmVideoPathZip()).isNull();
        assertThat(video.getDateTimeVideoCreated()).isNull();
        assertThat(video.getDateTimeVideoProcessCompleted()).isNull();
        assertThat(video.getNmPersonName()).isNull();
    }
}
