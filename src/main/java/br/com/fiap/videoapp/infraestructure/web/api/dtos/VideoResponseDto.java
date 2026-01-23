package br.com.fiap.videoapp.infraestructure.web.api.dtos;

import br.com.fiap.videoapp.domain.models.VideoModel;

import java.time.Instant;

public record VideoResponseDto(
        String nmVideo,
        String cdVideoStatus,
        String nmVideoPathOrigin,
        String nmVideoPathZip,
        Instant dateTimeVideoCreated,
        Instant dateTimeVideoProcessCompleted
) {
    public VideoResponseDto(VideoModel videoModel) {
        this(videoModel.getNmVideo(),
                videoModel.getCdVideoStatus(),
                videoModel.getNmVideoPathOrigin(),
                videoModel.getNmVideoPathZip(),
                videoModel.getDateTimeVideoCreated(),
                videoModel.getDateTimeVideoProcessCompleted());
    }
}
