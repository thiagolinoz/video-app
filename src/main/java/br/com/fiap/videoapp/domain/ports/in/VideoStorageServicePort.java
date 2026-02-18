package br.com.fiap.videoapp.domain.ports.in;

import br.com.fiap.videoapp.domain.models.VideoDownloadModel;
import org.springframework.web.multipart.MultipartFile;

public interface VideoStorageServicePort {
    void store(MultipartFile file, String email);
    VideoDownloadModel downloadVideo(String email, String idVideo);
}
