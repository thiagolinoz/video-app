package br.com.fiap.videoapp.infraestructure.event.messages;

import java.util.Date;

public record VideoUploadedMessage(
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
    @Override
    public String toString() {
        return "VideoUploadedModel{" +
                "nmPessoaEmail='" + nmPessoaEmail + '\'' +
                ", idVideoSend='" + idVideoSend + '\'' +
                ", cdVideoStatus='" + cdVideoStatus + '\'' +
                ", nmVideo='" + nmVideo + '\'' +
                ", nmVideoPathOrigin='" + nmVideoPathOrigin + '\'' +
                ", nmVideoPathZip='" + nmVideoPathZip + '\'' +
                ", dateTimeVideoCreated=" + dateTimeVideoCreated +
                ", dateTimeVideoProcessCompleted=" + dateTimeVideoProcessCompleted +
                ", nmPersonName='" + nmPersonName + '\'' +
                '}';
    }
}
