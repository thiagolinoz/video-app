package br.com.fiap.videoapp.infraestructure.web.api.controllers;

import br.com.fiap.videoapp.domain.models.VideoDownloadModel;
import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.domain.ports.in.VideoMetadataServicePort;
import br.com.fiap.videoapp.domain.ports.in.VideoStorageServicePort;
import br.com.fiap.videoapp.infraestructure.commons.mappers.VideoMapper;
import br.com.fiap.videoapp.infraestructure.web.api.dtos.VideoResponseDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URI;
import java.util.List;

@Service
@RestController
@RequestMapping("/api/v1")
public class VideoController {

    private final VideoMetadataServicePort videoMetadataServicePort;

    private final VideoStorageServicePort videoStorageServicePort;

    public VideoController(VideoMetadataServicePort videoMetadataServicePort,
                           VideoStorageServicePort videoStorageServicePort) {
        this.videoMetadataServicePort = videoMetadataServicePort;
        this.videoStorageServicePort = videoStorageServicePort;
    }

    @GetMapping("/user/{email}/videos")
    public ResponseEntity<List<VideoResponseDto>> listVideos(@PathVariable("email") String email) {
        List<VideoModel> videoModel = videoMetadataServicePort.listVideos(email);

        return ResponseEntity.ok(VideoMapper.toListResponse(videoModel));
    }

    @PostMapping("/user/{email}/videos/upload")
    public ResponseEntity<Void> uploadVideo(@RequestParam("file") MultipartFile file, @PathVariable("email") String email) {
        videoStorageServicePort.store(file, email);
        return ResponseEntity.created(URI.create("/api/v1/user/videos/upload")).build();
    }

    @GetMapping("/user/{email}/videos/{idVideo}/download")
    public ResponseEntity<InputStreamResource> downloadVideo(@PathVariable("email") String email, @PathVariable("idVideo") String idVideo) {
        VideoDownloadModel stream = videoMetadataServicePort.downloadVideo(email, idVideo);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + stream.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(stream.getVideo()));
    }


}
