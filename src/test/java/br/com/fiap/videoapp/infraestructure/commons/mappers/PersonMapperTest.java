package br.com.fiap.videoapp.infraestructure.commons.mappers;

import br.com.fiap.videoapp.domain.models.PersonModel;
import br.com.fiap.videoapp.infraestructure.persistence.entities.PersonEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PersonMapperTest {

    @Test
    @DisplayName("Deve converter PersonEntity para PersonModel corretamente")
    void toModel_Success() {
        PersonEntity entity = new PersonEntity("test@email.com", "Test User", "secret");

        PersonModel model = PersonMapper.toModel(entity);

        assertThat(model).isNotNull();
        assertThat(model.getNmEmail()).isEqualTo("test@email.com");
        assertThat(model.getNmName()).isEqualTo("Test User");
        assertThat(model.getCdPassword()).isEqualTo("secret");
    }

    @Test
    @DisplayName("Deve converter PersonModel para PersonEntity corretamente")
    void toEntity_Success() {
        PersonModel model = new PersonModel("test@email.com", "Test User", "secret");

        PersonEntity entity = PersonMapper.toEntity(model);

        assertThat(entity).isNotNull();
        assertThat(entity.getNmEmail()).isEqualTo("test@email.com");
        assertThat(entity.getNmName()).isEqualTo("Test User");
        assertThat(entity.getCdPassword()).isEqualTo("secret");
    }

    @Test
    @DisplayName("Deve preservar todos os campos na conversão bidirecional")
    void roundTrip_PreservesAllFields() {
        PersonModel original = new PersonModel("round@email.com", "Round Trip", "pass");

        PersonEntity entity = PersonMapper.toEntity(original);
        PersonModel result = PersonMapper.toModel(entity);

        assertThat(result.getNmEmail()).isEqualTo(original.getNmEmail());
        assertThat(result.getNmName()).isEqualTo(original.getNmName());
        assertThat(result.getCdPassword()).isEqualTo(original.getCdPassword());
    }
}
