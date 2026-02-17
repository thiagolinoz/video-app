package br.com.fiap.videoapp.domain.ports.out;

import br.com.fiap.videoapp.domain.models.VideoModel;

public interface FileEventPublisherPort {
    void publishNewVideo(VideoModel videoModel);
    void publishStatusVideo(VideoModel videoModel);
}
