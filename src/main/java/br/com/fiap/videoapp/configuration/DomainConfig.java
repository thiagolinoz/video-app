package br.com.fiap.videoapp.configuration;

import br.com.fiap.videoapp.domain.ports.out.FileEventPublisherPort;
import br.com.fiap.videoapp.domain.ports.out.PersonRepositoryPort;
import br.com.fiap.videoapp.domain.ports.out.VideoMetadaRepositoryPort;
import br.com.fiap.videoapp.domain.ports.out.VideoStorageRepositoryPort;
import br.com.fiap.videoapp.domain.services.PersonService;
import br.com.fiap.videoapp.domain.services.VideoMetadataService;
import br.com.fiap.videoapp.domain.services.VideoStorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public PersonService personService(PersonRepositoryPort personRepositoryPort) {
        return new PersonService(personRepositoryPort);
    }

    @Bean
    public VideoMetadataService videoService(VideoMetadaRepositoryPort videoMetadaRepositoryPort) {
        return new VideoMetadataService(videoMetadaRepositoryPort);
    }

    @Bean
    public VideoStorageService videoStorageService(VideoStorageRepositoryPort videoStorageRepositoryPort,
                                                   FileEventPublisherPort fileEventPublisherPort,
                                                   PersonRepositoryPort personRepositoryPort) {
        return new VideoStorageService(videoStorageRepositoryPort, fileEventPublisherPort, personRepositoryPort);
    }
}
