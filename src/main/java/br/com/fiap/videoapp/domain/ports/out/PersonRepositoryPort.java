package br.com.fiap.videoapp.domain.ports.out;

import br.com.fiap.videoapp.domain.models.PersonModel;

import java.util.Optional;

public interface PersonRepositoryPort {
    PersonModel createPerson(PersonModel person);
    Optional<PersonModel> getPersonByEmail(String email);
}
