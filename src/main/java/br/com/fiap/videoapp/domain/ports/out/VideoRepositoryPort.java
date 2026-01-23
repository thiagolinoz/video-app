package br.com.fiap.videoapp.domain.ports.out;

import br.com.fiap.videoapp.domain.models.VideoModel;

import java.util.List;
import java.util.Optional;

public interface VideoRepositoryPort {
    List<VideoModel> listVideos(String email);
}
