package br.com.fiap.videoapp.infraestructure.commons.mappers;

import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.infraestructure.persistence.entities.VideoEntity;
import br.com.fiap.videoapp.infraestructure.web.api.dtos.VideoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class VideoMapperTest {

    private VideoEntity videoEntity;
    private VideoModel videoModel;
    private Instant now;

    @BeforeEach
    void setUp() {
        now = Instant.now();

        videoEntity = new VideoEntity();
        videoEntity.setNmPessoaEmail("user@email.com");
        videoEntity.setIdVideoSend("video-123");
        videoEntity.setCdVideoStatus("RECEIVED");
        videoEntity.setNmVideo("meu-video.mp4");
        videoEntity.setNmVideoPathOrigin("videos/user@email.com/meu-video.mp4");
        videoEntity.setNmVideoPathZip("videos/user@email.com/meu-video.zip");
        videoEntity.setDateTimeVideoCreated(now);
        videoEntity.setDateTimeVideoProcessCompleted(null);
        videoEntity.setNmPersonName("User Name");

        videoModel = new VideoModel("user@email.com", "video-123", "RECEIVED",
                "meu-video.mp4", "videos/user@email.com/meu-video.mp4",
                "videos/user@email.com/meu-video.zip", now, null, "User Name");
    }

    @Test
    @DisplayName("Deve converter VideoEntity para VideoModel corretamente")
    void toModel_Success() {
        VideoModel result = VideoMapper.toModel(videoEntity);

        assertThat(result).isNotNull();
        assertThat(result.getIdVideoSend()).isEqualTo("video-123");
        assertThat(result.getNmVideo()).isEqualTo("meu-video.mp4");
        assertThat(result.getCdVideoStatus()).isEqualTo("RECEIVED");
        assertThat(result.getNmVideoPathOrigin()).isEqualTo("videos/user@email.com/meu-video.mp4");
        assertThat(result.getNmVideoPathZip()).isEqualTo("videos/user@email.com/meu-video.zip");
        assertThat(result.getDateTimeVideoCreated()).isEqualTo(now);
        assertThat(result.getDateTimeVideoProcessCompleted()).isNull();
    }

    @Test
    @DisplayName("Deve converter VideoModel para VideoEntity corretamente")
    void toEntity_Success() {
        VideoEntity result = VideoMapper.toEntity(videoModel);

        assertThat(result).isNotNull();
        assertThat(result.getNmPessoaEmail()).isEqualTo("user@email.com");
        assertThat(result.getIdVideoSend()).isEqualTo("video-123");
        assertThat(result.getCdVideoStatus()).isEqualTo("RECEIVED");
        assertThat(result.getNmVideo()).isEqualTo("meu-video.mp4");
        assertThat(result.getNmVideoPathOrigin()).isEqualTo("videos/user@email.com/meu-video.mp4");
        assertThat(result.getNmVideoPathZip()).isEqualTo("videos/user@email.com/meu-video.zip");
        assertThat(result.getDateTimeVideoCreated()).isEqualTo(now);
        assertThat(result.getNmPersonName()).isEqualTo("User Name");
    }

    @Test
    @DisplayName("Deve converter lista de VideoModel para lista de VideoResponseDto")
    void toListResponse_Success() {
        List<VideoModel> models = List.of(videoModel);

        List<VideoResponseDto> result = VideoMapper.toListResponse(models);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).nmVideo()).isEqualTo("meu-video.mp4");
        assertThat(result.get(0).idVideoSend()).isEqualTo("video-123");
        assertThat(result.get(0).cdVideoStatus()).isEqualTo("RECEIVED");
    }

    @Test
    @DisplayName("Deve retornar lista vazia ao converter lista vazia")
    void toListResponse_EmptyList() {
        List<VideoResponseDto> result = VideoMapper.toListResponse(List.of());

        assertThat(result).isEmpty();
    }
}
