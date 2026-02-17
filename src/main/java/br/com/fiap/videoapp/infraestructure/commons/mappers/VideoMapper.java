package br.com.fiap.videoapp.infraestructure.commons.mappers;

import br.com.fiap.videoapp.domain.enums.VideoStatusEnum;
import br.com.fiap.videoapp.domain.models.PersonModel;
import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.domain.models.events.VideoUploadedModel;
import br.com.fiap.videoapp.infraestructure.persistence.entities.VideoEntity;
import br.com.fiap.videoapp.infraestructure.web.api.dtos.VideoResponseDto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
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

    public static VideoEntity toEntity(VideoModel m) {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setNmPessoaEmail(m.getNmPersonEmail());
        videoEntity.setIdVideoSend(m.getIdVideoSend());
        videoEntity.setCdVideoStatus(m.getCdVideoStatus());
        videoEntity.setNmVideo(m.getNmVideo());
        videoEntity.setNmVideoPathOrigin(m.getNmVideoPathOrigin());
        videoEntity.setNmVideoPathZip(m.getNmVideoPathZip());
        videoEntity.setDateTimeVideoCreated(m.getDateTimeVideoCreated());
        videoEntity.setDateTimeVideoProcessCompleted(m.getDateTimeVideoProcessCompleted());
        videoEntity.setNmPersonName(m.getNmPersonName());
        return videoEntity;
    }

    public static List<VideoResponseDto> toListResponse(List<VideoModel> videoModels) {
        List<VideoResponseDto> listVideos = new ArrayList<>();
        videoModels.forEach(video -> listVideos.add(new VideoResponseDto(video)));

        return listVideos;
    }
}
