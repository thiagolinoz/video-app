package br.com.fiap.videoapp.domain.ports.out;

import br.com.fiap.videoapp.domain.models.PersonModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface VideoStorageRepositoryPort {
    String store(MultipartFile file, String fileNameStorage, String path, PersonModel personModel);
    InputStream download(String nmVideoPathOrigin);
}
