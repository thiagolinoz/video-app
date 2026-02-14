package br.com.fiap.videoapp.infraestructure.commons.mappers;

import java.time.Instant;
import java.util.Date;

import br.com.fiap.videoapp.domain.enums.VideoStatusEnum;
import br.com.fiap.videoapp.domain.models.PersonModel;
import br.com.fiap.videoapp.domain.models.events.VideoUploadedModel;

public class VideoUploadedMapper {

    public static VideoUploadedModel toVideoUploadedModel(String idVideo,
                                                          String fileNameStorage,
                                                          VideoStatusEnum videoStatus,
                                                          PersonModel person) {
        return new VideoUploadedModel(
                person.getNmEmail(),
                idVideo,
                videoStatus.name(),
                fileNameStorage,
                "paths3/" + fileNameStorage,
                null,
                Date.from(Instant.now()),
                null,
                person.getNmName()
        );
    }
}
