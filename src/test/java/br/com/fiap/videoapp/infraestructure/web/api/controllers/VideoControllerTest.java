package br.com.fiap.videoapp.infraestructure.web.api.controllers;

import br.com.fiap.videoapp.domain.models.VideoDownloadModel;
import br.com.fiap.videoapp.domain.models.VideoModel;
import br.com.fiap.videoapp.domain.ports.in.VideoMetadataServicePort;
import br.com.fiap.videoapp.domain.ports.in.VideoStorageServicePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VideoController.class)
class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideoMetadataServicePort videoMetadataServicePort;

    @MockBean
    private VideoStorageServicePort videoStorageServicePort;

    @Test
    @DisplayName("Deve listar vídeos do usuário e retornar 200")
    void listVideos_Returns200() throws Exception {
        VideoModel video = new VideoModel("user@email.com", "video-1", "RECEIVED",
                "video.mp4", "path/origin", null, Instant.now(), null, "User");

        when(videoMetadataServicePort.listVideos("user@email.com")).thenReturn(List.of(video));

        mockMvc.perform(get("/api/v1/user/{email}/videos", "user@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nmVideo").value("video.mp4"))
                .andExpect(jsonPath("$[0].idVideoSend").value("video-1"))
                .andExpect(jsonPath("$[0].cdVideoStatus").value("RECEIVED"));

        verify(videoMetadataServicePort, times(1)).listVideos("user@email.com");
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando usuário não tem vídeos")
    void listVideos_EmptyList_Returns200() throws Exception {
        when(videoMetadataServicePort.listVideos("user@email.com")).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/user/{email}/videos", "user@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("Deve fazer upload de vídeo e retornar 201")
    void uploadVideo_Returns201() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.mp4", "video/mp4", "content".getBytes());

        doNothing().when(videoStorageServicePort).store(any(), eq("user@email.com"));

        mockMvc.perform(multipart("/api/v1/user/{email}/videos/upload", "user@email.com")
                        .file(file))
                .andExpect(status().isCreated());

        verify(videoStorageServicePort, times(1)).store(any(), eq("user@email.com"));
    }

    @Test
    @DisplayName("Deve fazer download do vídeo e retornar 200 com stream")
    void downloadVideo_Returns200() throws Exception {
        VideoDownloadModel downloadModel = new VideoDownloadModel(
                "video.zip", new ByteArrayInputStream("zip-bytes".getBytes()));

        when(videoStorageServicePort.downloadVideo("user@email.com", "video-id")).thenReturn(downloadModel);

        mockMvc.perform(get("/api/v1/user/{email}/videos/{idVideo}/download",
                        "user@email.com", "video-id"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"video.zip\""))
                .andExpect(content().contentType("application/octet-stream"));

        verify(videoStorageServicePort, times(1)).downloadVideo("user@email.com", "video-id");
    }
}
