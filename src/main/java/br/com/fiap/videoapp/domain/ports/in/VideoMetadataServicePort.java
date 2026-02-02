package br.com.fiap.videoapp.domain.ports.in;

import br.com.fiap.videoapp.domain.models.VideoModel;

import java.util.List;

public interface VideoMetadataServicePort {
    List<VideoModel> listVideos(String email);
}
