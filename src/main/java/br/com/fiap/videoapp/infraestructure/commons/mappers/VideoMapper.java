package br.com.fiap.videoapp.infraestructure.commons.mappers;

import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.infraestructure.persistence.entities.VideoEntity;
import br.com.fiap.videoapp.infraestructure.web.api.dtos.VideoResponseDto;

import java.util.ArrayList;
import java.util.List;

public class VideoMapper {

    public static VideoModel toModel(VideoEntity video) {
        VideoModel videoModel = new VideoModel();
        videoModel.setNmVideo(video.getNmVideo());
        videoModel.setCdVideoStatus(video.getCdVideoStatus());
        videoModel.setNmVideoPathOrigin(video.getNmVideoPathOrigin());
        videoModel.setNmVideoPathZip(video.getNmVideoPathZip());
        videoModel.setDateTimeVideoCreated(video.getDateTimeVideoCreated());
        videoModel.setDateTimeVideoProcessCompleted(video.getDateTimeVideoProcessCompleted());
        return videoModel;
    }

    public static List<VideoResponseDto> toListResponse(List<VideoModel> videoModels) {
        List<VideoResponseDto> listVideos = new ArrayList<>();
        videoModels.forEach(video -> listVideos.add(new VideoResponseDto(video)));

        return listVideos;
    }
}
