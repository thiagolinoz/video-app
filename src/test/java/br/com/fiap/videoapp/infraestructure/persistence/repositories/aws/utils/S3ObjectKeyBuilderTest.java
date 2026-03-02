package br.com.fiap.videoapp.infraestructure.persistence.repositories.aws.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class S3ObjectKeyBuilderTest {

    private S3ObjectKeyBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new S3ObjectKeyBuilder();
    }

    @Test
    @DisplayName("Deve gerar chave S3 no formato path/user/filename")
    void buildVideoKey_BasicFormat() {
        String key = builder.buildVideoKey("videos", "user@email.com", "video.mp4");

        assertThat(key).isEqualTo("videos/user@email.com/video.mp4");
    }

    @Test
    @DisplayName("Deve converter nome do arquivo para minúsculas")
    void buildVideoKey_LowercasesFilename() {
        String key = builder.buildVideoKey("videos", "user@email.com", "MyVideo.MP4");

        assertThat(key).contains("myvideo.mp4");
    }

    @Test
    @DisplayName("Deve substituir espaços por hífens")
    void buildVideoKey_ReplacesSpacesWithHyphens() {
        String key = builder.buildVideoKey("videos", "user@email.com", "my video file.mp4");

        assertThat(key).contains("my-video-file.mp4");
    }

    @Test
    @DisplayName("Deve remover acentos e caracteres especiais")
    void buildVideoKey_RemovesAccents() {
        String key = builder.buildVideoKey("videos", "user@email.com", "Vídeo Ação.mp4");

        assertThat(key).doesNotContain("é", "ã");
        assertThat(key).contains(".mp4");
    }

    @Test
    @DisplayName("Deve remover caracteres não ASCII")
    void buildVideoKey_RemovesNonAsciiChars() {
        String key = builder.buildVideoKey("videos", "user@email.com", "video ñ special.mp4");

        assertThat(key).doesNotContain("ñ");
    }

    @Test
    @DisplayName("Deve montar caminho corretamente com path aninhado")
    void buildVideoKey_WithNestedPath() {
        String key = builder.buildVideoKey("uploads/raw", "test@domain.com", "clip.mp4");

        assertThat(key).startsWith("uploads/raw/test@domain.com/");
        assertThat(key).endsWith("clip.mp4");
    }
}
