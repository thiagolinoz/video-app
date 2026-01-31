package br.com.fiap.videoapp.domain.ports.out;

import org.springframework.web.multipart.MultipartFile;

public interface VideoStorageRepositoryPort {
    void store(MultipartFile file, String fileNameStorage);
}
