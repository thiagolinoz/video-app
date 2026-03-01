package br.com.fiap.videoapp.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PersonModel - Testes unitários")
class PersonModelTest {

    @Test
    @DisplayName("Deve criar PersonModel com construtor padrão e definir valores via setters")
    void shouldCreateWithDefaultConstructorAndSetters() {
        PersonModel person = new PersonModel();
        person.setNmEmail("user@email.com");
        person.setNmName("User Name");
        person.setCdPassword("secret");

        assertThat(person.getNmEmail()).isEqualTo("user@email.com");
        assertThat(person.getNmName()).isEqualTo("User Name");
        assertThat(person.getCdPassword()).isEqualTo("secret");
    }

    @Test
    @DisplayName("Deve criar PersonModel com construtor parametrizado")
    void shouldCreateWithParameterizedConstructor() {
        PersonModel person = new PersonModel("a@b.com", "Alice", "pass123");

        assertThat(person.getNmEmail()).isEqualTo("a@b.com");
        assertThat(person.getNmName()).isEqualTo("Alice");
        assertThat(person.getCdPassword()).isEqualTo("pass123");
    }

    @Test
    @DisplayName("Deve criar PersonModel com Builder e retornar valores corretos")
    void shouldCreateWithBuilder() {
        PersonModel person = new PersonModel.Builder()
                .setNmEmail("builder@test.com")
                .setNmName("Builder User")
                .setCdPassword("builderPass")
                .build();

        assertThat(person.getNmEmail()).isEqualTo("builder@test.com");
        assertThat(person.getNmName()).isEqualTo("Builder User");
        assertThat(person.getCdPassword()).isEqualTo("builderPass");
    }

    @Test
    @DisplayName("Deve retornar null para campos não definidos no construtor padrão")
    void shouldReturnNullForUninitializedFields() {
        PersonModel person = new PersonModel();

        assertThat(person.getNmEmail()).isNull();
        assertThat(person.getNmName()).isNull();
        assertThat(person.getCdPassword()).isNull();
    }

    @Test
    @DisplayName("Deve atualizar valores corretamente via setters")
    void shouldUpdateFieldsViaSetters() {
        PersonModel person = new PersonModel("old@email.com", "Old Name", "oldPass");

        person.setNmEmail("new@email.com");
        person.setNmName("New Name");
        person.setCdPassword("newPass");

        assertThat(person.getNmEmail()).isEqualTo("new@email.com");
        assertThat(person.getNmName()).isEqualTo("New Name");
        assertThat(person.getCdPassword()).isEqualTo("newPass");
    }

    @Test
    @DisplayName("Builder deve retornar null para campos não preenchidos")
    void builderShouldReturnNullForUnsetFields() {
        PersonModel person = new PersonModel.Builder().build();

        assertThat(person.getNmEmail()).isNull();
        assertThat(person.getNmName()).isNull();
        assertThat(person.getCdPassword()).isNull();
    }
}
