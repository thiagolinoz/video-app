package br.com.fiap.videoapp.infraestructure.commons.mappers;

import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.infraestructure.event.messages.VideoUpdateStatusMessage;
import br.com.fiap.videoapp.infraestructure.event.messages.VideoUploadedMessage;

import java.util.Date;

public class VideoUpdateStatusMessageMapper {

    public static VideoUpdateStatusMessage toMessage(VideoModel videoModel) {
        return new VideoUpdateStatusMessage(
                videoModel.getNmPersonEmail(),
                videoModel.getIdVideoSend(),
                videoModel.getCdVideoStatus(),
                videoModel.getNmVideo(),
                videoModel.getNmPersonName()
        );
    }

}
