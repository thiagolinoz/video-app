package br.com.fiap.videoapp.domain.ports.in;

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
@DisplayName("PersonServicePort - Testes de contrato da interface")
class PersonServicePortTest {

    @Mock
    private PersonServicePort personServicePort;

    @Test
    @DisplayName("createPerson deve ser chamado e retornar PersonModel")
    void shouldCallCreatePersonAndReturnModel() {
        PersonModel person = new PersonModel("a@b.com", "Alice", "pass");
        when(personServicePort.createPerson(person)).thenReturn(person);

        PersonModel result = personServicePort.createPerson(person);

        assertThat(result).isNotNull();
        assertThat(result.getNmEmail()).isEqualTo("a@b.com");
        verify(personServicePort, times(1)).createPerson(person);
    }

    @Test
    @DisplayName("getPersonByEmail deve ser chamado e retornar Optional com PersonModel")
    void shouldCallGetPersonByEmailAndReturnOptional() {
        PersonModel person = new PersonModel("a@b.com", "Alice", "pass");
        when(personServicePort.getPersonByEmail("a@b.com")).thenReturn(Optional.of(person));

        Optional<PersonModel> result = personServicePort.getPersonByEmail("a@b.com");

        assertThat(result).isPresent();
        assertThat(result.get().getNmEmail()).isEqualTo("a@b.com");
        verify(personServicePort, times(1)).getPersonByEmail("a@b.com");
    }

    @Test
    @DisplayName("getPersonByEmail deve retornar Optional vazio quando pessoa não existe")
    void shouldReturnEmptyOptionalWhenPersonNotFound() {
        when(personServicePort.getPersonByEmail("notfound@test.com")).thenReturn(Optional.empty());

        Optional<PersonModel> result = personServicePort.getPersonByEmail("notfound@test.com");

        assertThat(result).isEmpty();
        verify(personServicePort, times(1)).getPersonByEmail("notfound@test.com");
    }
}
