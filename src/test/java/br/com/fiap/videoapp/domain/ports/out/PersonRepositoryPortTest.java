package br.com.fiap.videoapp.domain.ports.out;

import br.com.fiap.videoapp.domain.models.PersonModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PersonRepositoryPort - Testes de contrato da interface")
class PersonRepositoryPortTest {

    @Mock
    private PersonRepositoryPort personRepositoryPort;

    @Test
    @DisplayName("createPerson deve ser chamado e retornar PersonModel persistido")
    void shouldCallCreatePersonAndReturnModel() {
        PersonModel person = new PersonModel("repo@test.com", "Repo User", "secret");
        when(personRepositoryPort.createPerson(person)).thenReturn(person);

        PersonModel result = personRepositoryPort.createPerson(person);

        assertThat(result).isNotNull();
        assertThat(result.getNmEmail()).isEqualTo("repo@test.com");
        verify(personRepositoryPort, times(1)).createPerson(person);
    }

    @Test
    @DisplayName("getPersonByEmail deve retornar Optional com PersonModel quando encontrado")
    void shouldReturnOptionalPresentWhenFound() {
        PersonModel person = new PersonModel("found@test.com", "Found User", "pass");
        when(personRepositoryPort.getPersonByEmail("found@test.com")).thenReturn(Optional.of(person));

        Optional<PersonModel> result = personRepositoryPort.getPersonByEmail("found@test.com");

        assertThat(result).isPresent();
        assertThat(result.get().getNmEmail()).isEqualTo("found@test.com");
        verify(personRepositoryPort, times(1)).getPersonByEmail("found@test.com");
    }

    @Test
    @DisplayName("getPersonByEmail deve retornar Optional vazio quando não encontrado")
    void shouldReturnEmptyOptionalWhenNotFound() {
        when(personRepositoryPort.getPersonByEmail("missing@test.com")).thenReturn(Optional.empty());

        Optional<PersonModel> result = personRepositoryPort.getPersonByEmail("missing@test.com");

        assertThat(result).isEmpty();
        verify(personRepositoryPort, times(1)).getPersonByEmail("missing@test.com");
    }
}
