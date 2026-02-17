package br.com.fiap.videoapp.infraestructure.event.messages;

import java.util.Date;

public record VideoUpdateStatusMessage(String nmPersonEmail,
                                       String idVideoSend,
                                       String cdVideoStatus,
                                       String nmVideo,
                                       String nmPersonName) {
    @Override
    public String toString() {
        return "VideoUpdateStatusMessage{" +
                "nmPersonEmail='" + nmPersonEmail + '\'' +
                ", idVideoSend='" + idVideoSend + '\'' +
                ", cdVideoStatus='" + cdVideoStatus + '\'' +
                ", nmVideo='" + nmVideo + '\'' +
                ", nmPersonName='" + nmPersonName + '\'' +
                '}';
    }
}
