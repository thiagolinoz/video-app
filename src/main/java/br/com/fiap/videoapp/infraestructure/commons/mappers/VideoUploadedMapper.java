package br.com.fiap.videoapp.infraestructure.commons.mappers;

import java.util.Date;

import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.domain.models.events.VideoUploadedModel;

public class VideoUploadedMapper {

//    public static VideoUploadedModel toVideoUploadedModel(String idVideo,
//                                                          String fileNameStorage,
//                                                          String savedFilePath,
//                                                          VideoStatusEnum videoStatus,
//                                                          PersonModel person) {
//        return new VideoUploadedModel(
//                person.getNmEmail(),
//                idVideo,
//                videoStatus.name(),
//                fileNameStorage,
//                savedFilePath,
//                null,
//                Date.from(Instant.now()),
//                null,
//                person.getNmName()
//        );
//    }

    public static VideoUploadedModel toVideoUploadedModel(VideoModel videoModel) {
        return new VideoUploadedModel(
                videoModel.getNmPersonEmail(),
                videoModel.getIdVideoSend(),
                videoModel.getCdVideoStatus(),
                videoModel.getNmVideo(),
                videoModel.getNmVideoPathOrigin(),
                null,
                Date.from(videoModel.getDateTimeVideoCreated()),
                null,
                videoModel.getNmPersonName()
        );
    }
}
