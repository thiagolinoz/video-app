package br.com.fiap.videoapp.infraestructure.web.api.controllers;

import br.com.fiap.videoapp.domain.models.PersonModel;
import br.com.fiap.videoapp.domain.models.VideoDownloadModel;
import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.domain.ports.in.VideoMetadataServicePort;
import br.com.fiap.videoapp.domain.ports.in.VideoStorageServicePort;
import br.com.fiap.videoapp.infraestructure.commons.mappers.VideoMapper;
import br.com.fiap.videoapp.infraestructure.web.api.dtos.VideoResponseDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URI;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<VideoResponseDto>> listVideos(@PathVariable("email") String email, @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {

        if (isAuth(authorizationHeader,email)) {
            List<VideoModel> videoModel = videoMetadataServicePort.listVideos(email);

            return ResponseEntity.ok(VideoMapper.toListResponse(videoModel));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


    }

    @PostMapping("/user/{email}/videos/upload")
    public ResponseEntity<Void> uploadVideo(@RequestParam("file") MultipartFile file, @PathVariable("email") String email) {
        //if (isAuth(authorizationHeader,email)) {
            videoStorageServicePort.store(file, email);

            return ResponseEntity.created(URI.create("/api/v1/user/videos/upload")).build();
        //} else {
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        //}
    }

    @GetMapping("/user/{email}/videos/{idVideo}/download")
    public ResponseEntity<InputStreamResource> downloadVideo(@PathVariable("email") String email, @PathVariable("idVideo") String idVideo, @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if(isAuth(authorizationHeader,email)) {
            VideoDownloadModel stream = videoStorageServicePort.downloadVideo(email, idVideo);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + stream.getName() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(stream.getVideo()));
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public boolean isAuth(String authorization, String email) {

        // 1. Verifica se o header não é nulo e começa com "Basic "
        if (authorization == null || !authorization.startsWith("Basic ")) {
            return false;
        }

        try {
            // 2. Remove o prefixo "Basic " e decodifica Base64
            String base64Credentials = authorization.substring("Basic ".length());
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            String decodedCredentials = new String(decodedBytes); // formato "user:password"

            // 3. Separa username e password
            String[] parts = decodedCredentials.split(":", 2); // máximo 2 partes
            if (parts.length != 2) {
                return false;
            }

            String username = parts[0];
            String password = parts[1];

            Optional<PersonModel> user = videoStorageServicePort.getUser(username);
            if(user.isPresent()){
                return user.get().getNmEmail().equals(username) && user.get().getCdPassword().equals(password) && user.get().getNmEmail().equals(email);
            }else {
                return false;
            }

        } catch (IllegalArgumentException e) {
            // falha ao decodificar Base64
            return false;
        }
    }

}
