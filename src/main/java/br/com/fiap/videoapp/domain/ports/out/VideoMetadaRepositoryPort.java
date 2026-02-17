package br.com.fiap.videoapp.domain.ports.out;

import br.com.fiap.videoapp.domain.models.VideoModel;

import java.util.List;

public interface VideoMetadaRepositoryPort {
    List<VideoModel> listVideos(String email);
    VideoModel save(VideoModel video);
}
