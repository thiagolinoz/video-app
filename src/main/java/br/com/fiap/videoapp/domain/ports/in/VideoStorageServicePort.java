package br.com.fiap.videoapp.domain.ports.in;

import br.com.fiap.videoapp.domain.models.PersonModel;
import br.com.fiap.videoapp.domain.models.VideoDownloadModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface VideoStorageServicePort {
    void store(MultipartFile file, String email);
    Optional<PersonModel> getUser(String email);
    VideoDownloadModel downloadVideo(String email, String idVideo);
}
