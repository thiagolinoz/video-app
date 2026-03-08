package br.com.fiap.videoapp.domain.services;

import br.com.fiap.videoapp.domain.models.PersonModel;
import br.com.fiap.videoapp.domain.ports.out.PersonRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepositoryPort personRepositoryPort;

    @InjectMocks
    private PersonService personService;

    private PersonModel personModel;

    @BeforeEach
    void setUp() {
        personModel = new PersonModel("test@email.com", "Test User", "password123");
    }

    @Test
    @DisplayName("Deve criar pessoa com sucesso quando e-mail não existe")
    void createPerson_Success() {
        when(personRepositoryPort.getPersonByEmail("test@email.com")).thenReturn(Optional.empty());
        when(personRepositoryPort.createPerson(any(PersonModel.class))).thenReturn(personModel);

        PersonModel result = personService.createPerson(personModel);

        assertThat(result).isNotNull();
        assertThat(result.getNmEmail()).isEqualTo("test@email.com");
        assertThat(result.getNmName()).isEqualTo("Test User");

        verify(personRepositoryPort, times(1)).getPersonByEmail("test@email.com");
        verify(personRepositoryPort, times(1)).createPerson(personModel);
    }

    @Test
    @DisplayName("Deve lançar exceção quando pessoa já está cadastrada")
    void createPerson_AlreadyExists_ThrowsException() {
        when(personRepositoryPort.getPersonByEmail("test@email.com")).thenReturn(Optional.of(personModel));

        assertThatThrownBy(() -> personService.createPerson(personModel))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("This person is already signed up");

        verify(personRepositoryPort, never()).createPerson(any());
    }

    @Test
    @DisplayName("Deve retornar pessoa pelo e-mail quando encontrada")
    void getPersonByEmail_Found() {
        when(personRepositoryPort.getPersonByEmail("test@email.com")).thenReturn(Optional.of(personModel));

        Optional<PersonModel> result = personService.getPersonByEmail("test@email.com");

        assertThat(result).isPresent();
        assertThat(result.get().getNmEmail()).isEqualTo("test@email.com");
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando pessoa não encontrada")
    void getPersonByEmail_NotFound() {
        when(personRepositoryPort.getPersonByEmail("notfound@email.com")).thenReturn(Optional.empty());

        Optional<PersonModel> result = personService.getPersonByEmail("notfound@email.com");

        assertThat(result).isEmpty();
    }
}
