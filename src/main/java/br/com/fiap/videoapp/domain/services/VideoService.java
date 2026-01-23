package br.com.fiap.videoapp.domain.services;

import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.domain.ports.in.VideoServicePort;
import br.com.fiap.videoapp.domain.ports.out.VideoRepositoryPort;

import java.util.List;

public class VideoService implements VideoServicePort {

    private final VideoRepositoryPort videoRepositoryPort;

    public VideoService(VideoRepositoryPort videoRepositoryPort) {
        this.videoRepositoryPort = videoRepositoryPort;
    }

    @Override
    public List<VideoModel> listVideos(String email) {
        return videoRepositoryPort.listVideos(email);
    }
}
