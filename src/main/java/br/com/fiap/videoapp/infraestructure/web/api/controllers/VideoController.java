package br.com.fiap.videoapp.infraestructure.web.api.controllers;

import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.domain.ports.in.VideoMetadataServicePort;
import br.com.fiap.videoapp.domain.ports.in.VideoStorageServicePort;
import br.com.fiap.videoapp.infraestructure.commons.mappers.VideoMapper;
import br.com.fiap.videoapp.infraestructure.web.api.dtos.VideoResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/user/videos/{email}")
    public ResponseEntity<List<VideoResponseDto>> listVideos(@PathVariable("email") String email) {
        List<VideoModel> videoModel = videoMetadataServicePort.listVideos(email);

        return ResponseEntity.ok(VideoMapper.toListResponse(videoModel));
    }

    @PostMapping("/user/videos/upload/{email}")
    public ResponseEntity<Void> uploadVideo(@RequestParam("file") MultipartFile file, @PathVariable("email") String email) {
        videoStorageServicePort.store(file, email);
        return ResponseEntity.created(URI.create("/api/v1/user/videos/upload")).build();
    }
}
