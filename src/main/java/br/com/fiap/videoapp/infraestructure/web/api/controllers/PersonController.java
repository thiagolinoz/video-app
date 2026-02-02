package br.com.fiap.videoapp.infraestructure.web.api.controllers;

import br.com.fiap.videoapp.domain.models.PersonModel;
import br.com.fiap.videoapp.domain.ports.in.PersonServicePort;
import br.com.fiap.videoapp.infraestructure.web.api.dtos.PersonRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Service
@RestController
@RequestMapping("/api/v1")
public class PersonController {

    private final PersonServicePort personServicePort;

    public PersonController(PersonServicePort personServicePort) {
        this.personServicePort = personServicePort;
    }

    @PostMapping("/user/new")
    public ResponseEntity<Void> createPerson(@RequestBody PersonRequestDto personRequestDto) {
        PersonModel personModel = personServicePort.createPerson(toModel(personRequestDto));

        return ResponseEntity.created(URI.create("/api/v1/user/new")).build();
    }

    private PersonModel toModel(PersonRequestDto personRequestDto) {
        return new PersonModel(
                personRequestDto.nmEmail(),
                personRequestDto.nmName(),
                personRequestDto.cdPassword()
        );
    }
}
