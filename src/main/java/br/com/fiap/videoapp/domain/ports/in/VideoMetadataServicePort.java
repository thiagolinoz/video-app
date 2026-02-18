package br.com.fiap.videoapp.domain.ports.in;

import br.com.fiap.videoapp.domain.models.VideoDownloadModel;
import br.com.fiap.videoapp.domain.models.VideoModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface VideoMetadataServicePort {
    List<VideoModel> listVideos(String email);
}
