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
    private final VideoStorageRepositoryPort videoStorageRepositoryPort;

    public VideoMetadataService(VideoMetadaRepositoryPort videoMetadaRepositoryPort, VideoStorageRepositoryPort videoStorageRepositoryPort) {
        this.videoMetadaRepositoryPort = videoMetadaRepositoryPort;
        this.videoStorageRepositoryPort = videoStorageRepositoryPort;
    }

    @Override
    public List<VideoModel> listVideos(String email) {
        return videoMetadaRepositoryPort.listVideos(email);
    }

    @Override
    public VideoDownloadModel downloadVideo(String email, String idVideo) {
        Optional<VideoModel> videoModel = videoMetadaRepositoryPort.findBy(email, idVideo);

        if (videoModel.isEmpty()) throw new RuntimeException("This person dont have access to this video");

        InputStream file = videoStorageRepositoryPort.download(videoModel.get().getNmVideoPathOrigin());

        String fileName = extractFileName(videoModel.get().getNmVideoPathOrigin());

        return new VideoDownloadModel(fileName, file);
    }

    private String extractFileName(String key) {
        return key.substring(key.lastIndexOf("/") + 1);
    }
}
