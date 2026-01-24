package br.com.fiap.videoapp.domain.services;

import br.com.fiap.videoapp.domain.models.events.VideoUploadedModel;
import br.com.fiap.videoapp.domain.ports.in.VideoStorageServicePort;
import br.com.fiap.videoapp.domain.ports.out.FileEventPublisherPort;
import br.com.fiap.videoapp.domain.ports.out.VideoStorageRepositoryPort;
import org.springframework.web.multipart.MultipartFile;

public class VideoStorageService implements VideoStorageServicePort {

    private final VideoStorageRepositoryPort videoStorageRepositoryPort;
    private final FileEventPublisherPort fileEventPublisherPort;

    public VideoStorageService(VideoStorageRepositoryPort videoStorageRepositoryPort,
                               FileEventPublisherPort fileEventPublisherPort) {
        this.videoStorageRepositoryPort = videoStorageRepositoryPort;
        this.fileEventPublisherPort = fileEventPublisherPort;
    }

    @Override
    public void store(MultipartFile file) {
        VideoUploadedModel videoModel = videoStorageRepositoryPort.store(file);
        fileEventPublisherPort.publish(videoModel);
    }
}
