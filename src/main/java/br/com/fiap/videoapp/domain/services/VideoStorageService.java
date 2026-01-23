package br.com.fiap.videoapp.domain.services;

import br.com.fiap.videoapp.domain.ports.in.VideoStorageServicePort;
import br.com.fiap.videoapp.domain.ports.out.VideoStorageRepositoryPort;
import org.springframework.web.multipart.MultipartFile;

public class VideoStorageService implements VideoStorageServicePort {

    private final VideoStorageRepositoryPort videoStorageRepositoryPort;

    public VideoStorageService(VideoStorageRepositoryPort videoStorageRepositoryPort) {
        this.videoStorageRepositoryPort = videoStorageRepositoryPort;
    }

    @Override
    public void store(MultipartFile file) { videoStorageRepositoryPort.store(file); }
}
