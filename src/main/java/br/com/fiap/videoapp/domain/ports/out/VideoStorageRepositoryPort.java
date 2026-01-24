package br.com.fiap.videoapp.domain.ports.out;

import br.com.fiap.videoapp.domain.models.events.VideoUploadedModel;
import org.springframework.web.multipart.MultipartFile;

public interface VideoStorageRepositoryPort {
    VideoUploadedModel store(MultipartFile file);
}
