package br.com.fiap.videoapp.domain.ports.in;

import org.springframework.web.multipart.MultipartFile;

public interface VideoStorageServicePort {
    void store(MultipartFile file);
}
