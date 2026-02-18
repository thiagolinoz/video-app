package br.com.fiap.videoapp.domain.ports.out;

import br.com.fiap.videoapp.domain.models.VideoModel;

import java.util.List;
import java.util.Optional;

public interface VideoMetadaRepositoryPort {
    List<VideoModel> listVideos(String email);
    VideoModel save(VideoModel video);
    Optional<VideoModel> findBy(String email, String idVideo);
}
