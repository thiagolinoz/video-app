package br.com.fiap.videoapp.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("VideoDownloadModel - Testes unitários")
class VideoDownloadModelTest {

    @Test
    @DisplayName("Deve criar VideoDownloadModel com construtor padrão e setters")
    void shouldCreateWithDefaultConstructorAndSetters() throws Exception {
        InputStream stream = new ByteArrayInputStream("content".getBytes(StandardCharsets.UTF_8));
        VideoDownloadModel model = new VideoDownloadModel();
        model.setName("video.mp4");
        model.setVideo(stream);

        assertThat(model.getName()).isEqualTo("video.mp4");
        assertThat(model.getVideo()).isEqualTo(stream);
    }

    @Test
    @DisplayName("Deve criar VideoDownloadModel com construtor parametrizado")
    void shouldCreateWithParameterizedConstructor() {
        InputStream stream = new ByteArrayInputStream("data".getBytes(StandardCharsets.UTF_8));
        VideoDownloadModel model = new VideoDownloadModel("clip.mp4", stream);

        assertThat(model.getName()).isEqualTo("clip.mp4");
        assertThat(model.getVideo()).isEqualTo(stream);
    }

    @Test
    @DisplayName("Deve retornar null para campos não definidos no construtor padrão")
    void shouldReturnNullForUninitializedFields() {
        VideoDownloadModel model = new VideoDownloadModel();

        assertThat(model.getName()).isNull();
        assertThat(model.getVideo()).isNull();
    }

    @Test
    @DisplayName("Deve atualizar nome e stream via setters")
    void shouldUpdateFieldsViaSetters() {
        InputStream firstStream = new ByteArrayInputStream("first".getBytes(StandardCharsets.UTF_8));
        InputStream secondStream = new ByteArrayInputStream("second".getBytes(StandardCharsets.UTF_8));

        VideoDownloadModel model = new VideoDownloadModel("old.mp4", firstStream);
        model.setName("new.mp4");
        model.setVideo(secondStream);

        assertThat(model.getName()).isEqualTo("new.mp4");
        assertThat(model.getVideo()).isEqualTo(secondStream);
    }
}
