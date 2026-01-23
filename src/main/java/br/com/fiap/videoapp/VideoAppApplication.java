package br.com.fiap.videoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "br.com.fiap.videoapp",
        "br.com.fiap.videoapp.infrastructure.web.api"
})
public class VideoAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoAppApplication.class, args);
    }
}