package br.com.fiap.videoapp.infraestructure.event.messages;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record VideoUploadedMessage(
        String nmPersonEmail,
        String idVideoSend,
        String cdVideoStatus,
        String nmVideo,
        String nmVideoPathOrigin,
        String nmVideoPathZip,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        Date dateTimeVideoCreated,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        Date dateTimeVideoProcessCompleted,
        String nmPersonName
) {
    @Override
    public String toString() {
        return "VideoUploadedModel{" +
                "nmPersonEmail='" + nmPersonEmail + '\'' +
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
