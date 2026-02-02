package br.com.fiap.videoapp.domain.services;

import br.com.fiap.videoapp.domain.models.PersonModel;
import br.com.fiap.videoapp.domain.ports.in.PersonServicePort;
import br.com.fiap.videoapp.domain.ports.out.PersonRepositoryPort;

import java.util.Optional;

public class PersonService implements PersonServicePort {

    private final PersonRepositoryPort personRepositoryPort;

    public PersonService(PersonRepositoryPort personRepositoryPort) {
        this.personRepositoryPort = personRepositoryPort;
    }

    @Override
    public PersonModel createPerson(PersonModel person) {
        Optional<PersonModel> personByEmail = this.getPersonByEmail(person.getNmEmail());
        if (personByEmail.isPresent()) throw new RuntimeException("This person is already signed up");

        return personRepositoryPort.createPerson(person);
    }

    @Override
    public Optional<PersonModel> getPersonByEmail(String email) {
        return personRepositoryPort.getPersonByEmail(email);
    }
}
