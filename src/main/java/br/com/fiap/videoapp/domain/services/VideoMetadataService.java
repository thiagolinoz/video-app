package br.com.fiap.videoapp.domain.services;

import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.domain.ports.in.VideoMetadataServicePort;
import br.com.fiap.videoapp.domain.ports.out.VideoMetadaRepositoryPort;

import java.util.List;

public class VideoMetadataService implements VideoMetadataServicePort {

    private final VideoMetadaRepositoryPort videoMetadaRepositoryPort;

    public VideoMetadataService(VideoMetadaRepositoryPort videoMetadaRepositoryPort) {
        this.videoMetadaRepositoryPort = videoMetadaRepositoryPort;
    }

    @Override
    public List<VideoModel> listVideos(String email) {
        return videoMetadaRepositoryPort.listVideos(email);
    }
}
