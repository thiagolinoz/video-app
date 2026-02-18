package br.com.fiap.videoapp.domain.services;

import br.com.fiap.videoapp.domain.models.PersonModel;
import br.com.fiap.videoapp.domain.models.VideoDownloadModel;
import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.domain.ports.in.VideoMetadataServicePort;
import br.com.fiap.videoapp.domain.ports.out.VideoMetadaRepositoryPort;
import br.com.fiap.videoapp.domain.ports.out.VideoStorageRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
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
