package br.com.fiap.videoapp.domain.models;

import java.time.Instant;

public class VideoModel {
    private String nmPessoaEmail;
    private String idVideoSend;
    private String cdVideoStatus;
    private String nmVideo;
    private String nmVideoPathOrigin;
    private String nmVideoPathZip;
    private Instant dateTimeVideoCreated;
    private Instant dateTimeVideoProcessCompleted;
    private String nmPersonName;

    public VideoModel() {
    }

    public VideoModel(String nmPessoaEmail,
                      String idVideoSend,
                      String cdVideoStatus,
                      String nmVideo,
                      String nmVideoPathOrigin,
                      String nmVideoPathZip,
                      Instant dateTimeVideoCreated,
                      Instant dateTimeVideoProcessCompleted,
                      String nmPersonName) {
        this.nmPessoaEmail = nmPessoaEmail;
        this.idVideoSend = idVideoSend;
        this.cdVideoStatus = cdVideoStatus;
        this.nmVideo = nmVideo;
        this.nmVideoPathOrigin = nmVideoPathOrigin;
        this.nmVideoPathZip = nmVideoPathZip;
        this.dateTimeVideoCreated = dateTimeVideoCreated;
        this.dateTimeVideoProcessCompleted = dateTimeVideoProcessCompleted;
        this.nmPersonName = nmPersonName;
    }

    public String getNmPessoaEmail() {
        return nmPessoaEmail;
    }

    public void setNmPessoaEmail(String nmPessoaEmail) {
        this.nmPessoaEmail = nmPessoaEmail;
    }

    public String getIdVideoSend() {
        return idVideoSend;
    }

    public void setIdVideoSend(String idVideoSend) {
        this.idVideoSend = idVideoSend;
    }

    public String getCdVideoStatus() {
        return cdVideoStatus;
    }

    public void setCdVideoStatus(String cdVideoStatus) {
        this.cdVideoStatus = cdVideoStatus;
    }

    public String getNmVideo() {
        return nmVideo;
    }

    public void setNmVideo(String nmVideo) {
        this.nmVideo = nmVideo;
    }

    public String getNmVideoPathOrigin() {
        return nmVideoPathOrigin;
    }

    public void setNmVideoPathOrigin(String nmVideoPathOrigin) {
        this.nmVideoPathOrigin = nmVideoPathOrigin;
    }

    public String getNmVideoPathZip() {
        return nmVideoPathZip;
    }

    public void setNmVideoPathZip(String nmVideoPathZip) {
        this.nmVideoPathZip = nmVideoPathZip;
    }

    public Instant getDateTimeVideoCreated() {
        return dateTimeVideoCreated;
    }

    public void setDateTimeVideoCreated(Instant dateTimeVideoCreated) {
        this.dateTimeVideoCreated = dateTimeVideoCreated;
    }

    public Instant getDateTimeVideoProcessCompleted() {
        return dateTimeVideoProcessCompleted;
    }

    public void setDateTimeVideoProcessCompleted(Instant dateTimeVideoProcessCompleted) {
        this.dateTimeVideoProcessCompleted = dateTimeVideoProcessCompleted;
    }

    public String getNmPersonName() {
        return nmPersonName;
    }

    public void setNmPersonName(String nmPersonName) {
        this.nmPersonName = nmPersonName;
    }
}
