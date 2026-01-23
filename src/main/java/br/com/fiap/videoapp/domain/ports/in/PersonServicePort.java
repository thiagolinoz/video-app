package br.com.fiap.videoapp.domain.ports.in;

import br.com.fiap.videoapp.domain.models.PersonModel;

import java.util.Optional;

public interface PersonServicePort {
    PersonModel createPerson(PersonModel person);
    Optional<PersonModel> getPersonByEmail(String email);
}
