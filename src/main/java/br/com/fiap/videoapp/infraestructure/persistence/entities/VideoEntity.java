package br.com.fiap.videoapp.infraestructure.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class VideoEntity {
    private String nmPessoaEmail;
    private String idVideoSend;
    private String cdVideoStatus;
    private String nmVideo;
    private String nmVideoPathOrigin;
    private String nmVideoPathZip;
    private Instant dateTimeVideoCreated;
    private Instant dateTimeVideoProcessCompleted;
    private String nmPersonName;

    @DynamoDbPartitionKey
    public String getNmPessoaEmail() {
        return nmPessoaEmail;
    }

    public void setNmPessoaEmail(String nmPessoaEmail) {
        this.nmPessoaEmail = nmPessoaEmail;
    }

    @DynamoDbSortKey
    public String getIdVideoSend() {
        return idVideoSend;
    }

    public void setIdVideoSend(String idVideoSend) {
        this.idVideoSend = idVideoSend;
    }

    @DynamoDbSecondarySortKey(indexNames = "cdVideoStatusIndex")
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

    @Override
    public String toString() {
        return "VideoEntity{" +
                "nmPersonEmail='" + nmPessoaEmail + '\'' +
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
