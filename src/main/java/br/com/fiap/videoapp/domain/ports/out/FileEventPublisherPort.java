package br.com.fiap.videoapp.domain.ports.out;

import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.domain.models.events.VideoUploadedModel;

public interface FileEventPublisherPort {
    void publish(VideoUploadedModel videoModel);
}
