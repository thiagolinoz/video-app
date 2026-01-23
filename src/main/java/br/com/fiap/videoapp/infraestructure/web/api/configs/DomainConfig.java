package br.com.fiap.videoapp.infraestructure.web.api.configs;

import br.com.fiap.videoapp.domain.ports.out.PersonRepositoryPort;
import br.com.fiap.videoapp.domain.ports.out.VideoRepositoryPort;
import br.com.fiap.videoapp.domain.services.PersonService;
import br.com.fiap.videoapp.domain.services.VideoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public PersonService personService(PersonRepositoryPort personRepositoryPort) {
        return new PersonService(personRepositoryPort);
    }

    @Bean
    public VideoService videoService(VideoRepositoryPort videoRepositoryPort) {
        return new VideoService(videoRepositoryPort);
    }
}
