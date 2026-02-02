package br.com.fiap.videoapp.domain.models.events;

import java.time.Instant;

public record VideoUploadedModel(
        String nmPessoaEmail,
        String idVideoSend,
        String cdVideoStatus,
        String nmVideo,
        String nmVideoPathOrigin,
        String nmVideoPathZip,
        Instant dateTimeVideoCreated,
        Instant dateTimeVideoProcessCompleted,
        String nmPersonName
) {
}
