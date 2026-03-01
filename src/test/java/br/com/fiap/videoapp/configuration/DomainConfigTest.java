package br.com.fiap.videoapp.configuration;

import br.com.fiap.videoapp.domain.ports.out.FileEventPublisherPort;
import br.com.fiap.videoapp.domain.ports.out.PersonRepositoryPort;
import br.com.fiap.videoapp.domain.ports.out.VideoMetadaRepositoryPort;
import br.com.fiap.videoapp.domain.ports.out.VideoStorageRepositoryPort;
import br.com.fiap.videoapp.domain.services.PersonService;
import br.com.fiap.videoapp.domain.services.VideoStorageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("DomainConfig - Testes unitários")
class DomainConfigTest {

    @Mock
    private PersonRepositoryPort personRepositoryPort;

    @Mock
    private VideoMetadaRepositoryPort videoMetadaRepositoryPort;

    @Mock
    private VideoStorageRepositoryPort videoStorageRepositoryPort;

    @Mock
    private FileEventPublisherPort fileEventPublisherPort;

    private final DomainConfig domainConfig = new DomainConfig();

    @Test
    @DisplayName("personService() deve retornar instância não nula de PersonService")
    void shouldCreatePersonServiceBean() {
        PersonService service = domainConfig.personService(personRepositoryPort);

        assertThat(service).isNotNull();
        assertThat(service).isInstanceOf(PersonService.class);
    }

    @Test
    @DisplayName("videoStorageService() deve retornar instância não nula de VideoStorageService")
    void shouldCreateVideoStorageServiceBean() {
        VideoStorageService service = domainConfig.videoStorageService(
                videoMetadaRepositoryPort,
                videoStorageRepositoryPort,
                fileEventPublisherPort,
                personRepositoryPort
        );

        assertThat(service).isNotNull();
        assertThat(service).isInstanceOf(VideoStorageService.class);
    }

    @Test
    @DisplayName("personService() com mocks diferentes deve retornar instâncias distintas")
    void shouldReturnDistinctPersonServiceInstances() {
        PersonService first = domainConfig.personService(personRepositoryPort);
        PersonService second = domainConfig.personService(personRepositoryPort);

        assertThat(first).isNotNull();
        assertThat(second).isNotNull();
        assertThat(first).isNotSameAs(second);
    }
}
