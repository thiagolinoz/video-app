package br.com.fiap.videoapp.infraestructure.web.api.controllers;

import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.domain.ports.in.VideoServicePort;
import br.com.fiap.videoapp.infraestructure.commons.mappers.VideoMapper;
import br.com.fiap.videoapp.infraestructure.web.api.dtos.VideoResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
@RestController
@RequestMapping("/api/v1")
public class VideoController {

    private final VideoServicePort videoServicePort;

    public VideoController(VideoServicePort videoServicePort) {
        this.videoServicePort = videoServicePort;
    }

    @GetMapping("/user/videos")
    public ResponseEntity<List<VideoResponseDto>> listVideos() {
        List<VideoModel> videoModel = videoServicePort.listVideos("email@email.com");

        return ResponseEntity.ok(VideoMapper.toListResponse(videoModel));
    }
}
