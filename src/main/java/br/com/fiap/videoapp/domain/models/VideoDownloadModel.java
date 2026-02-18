package br.com.fiap.videoapp.domain.models;

import java.io.InputStream;

public class VideoDownloadModel {
    private String name;
    private InputStream video;

    public VideoDownloadModel() {}

    public VideoDownloadModel(String name, InputStream video) {
        this.name = name;
        this.video = video;
    }

    public InputStream getVideo() {
        return video;
    }

    public void setVideo(InputStream video) {
        this.video = video;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
