package br.com.fiap.videoapp.domain.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("VideoStatusEnum - Testes unitários")
class VideoStatusEnumTest {

    @Test
    @DisplayName("Deve conter exatamente os valores: RECEIVED, PROCESSING, COMPLETED, PROCESS_ERROR")
    void shouldContainAllExpectedValues() {
        VideoStatusEnum[] values = VideoStatusEnum.values();
        assertThat(values).hasSize(4);
        assertThat(values).containsExactly(
                VideoStatusEnum.RECEIVED,
                VideoStatusEnum.PROCESSING,
                VideoStatusEnum.COMPLETED,
                VideoStatusEnum.PROCESS_ERROR
        );
    }

    @Test
    @DisplayName("Deve retornar o nome correto para cada valor via name()")
    void shouldReturnCorrectName() {
        assertThat(VideoStatusEnum.RECEIVED.name()).isEqualTo("RECEIVED");
        assertThat(VideoStatusEnum.PROCESSING.name()).isEqualTo("PROCESSING");
        assertThat(VideoStatusEnum.COMPLETED.name()).isEqualTo("COMPLETED");
        assertThat(VideoStatusEnum.PROCESS_ERROR.name()).isEqualTo("PROCESS_ERROR");
    }

    @Test
    @DisplayName("Deve converter string válida para enum via valueOf()")
    void shouldResolveFromStringViaValueOf() {
        assertThat(VideoStatusEnum.valueOf("RECEIVED")).isEqualTo(VideoStatusEnum.RECEIVED);
        assertThat(VideoStatusEnum.valueOf("PROCESSING")).isEqualTo(VideoStatusEnum.PROCESSING);
        assertThat(VideoStatusEnum.valueOf("COMPLETED")).isEqualTo(VideoStatusEnum.COMPLETED);
        assertThat(VideoStatusEnum.valueOf("PROCESS_ERROR")).isEqualTo(VideoStatusEnum.PROCESS_ERROR);
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException para valor inválido no valueOf()")
    void shouldThrowExceptionForInvalidValueOf() {
        assertThatThrownBy(() -> VideoStatusEnum.valueOf("INVALID"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Deve retornar ordinal correto para cada valor")
    void shouldReturnCorrectOrdinal() {
        assertThat(VideoStatusEnum.RECEIVED.ordinal()).isZero();
        assertThat(VideoStatusEnum.PROCESSING.ordinal()).isEqualTo(1);
        assertThat(VideoStatusEnum.COMPLETED.ordinal()).isEqualTo(2);
        assertThat(VideoStatusEnum.PROCESS_ERROR.ordinal()).isEqualTo(3);
    }

    @Test
    @DisplayName("Deve retornar toString() igual ao name()")
    void shouldReturnToStringEqualToName() {
        for (VideoStatusEnum status : VideoStatusEnum.values()) {
            assertThat(status.toString()).isEqualTo(status.name());
        }
    }
}
