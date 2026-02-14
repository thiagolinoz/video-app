package br.com.fiap.videoapp.domain.models.events;

import java.time.Instant;
import java.util.Date;

public record VideoUploadedModel(
        String nmPessoaEmail,
        String idVideoSend,
        String cdVideoStatus,
        String nmVideo,
        String nmVideoPathOrigin,
        String nmVideoPathZip,
        Date dateTimeVideoCreated,
        Date dateTimeVideoProcessCompleted,
        String nmPersonName
) {
}
