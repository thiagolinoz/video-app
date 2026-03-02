package br.com.fiap.videoapp.infraestructure.web.api.dtos;

import br.com.fiap.videoapp.domain.models.PersonModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PersonRequestDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Deve criar PersonRequestDto com dados válidos sem violações")
    void valid_NoViolations() {
        PersonRequestDto dto = new PersonRequestDto("user@email.com", "User Name", "password123");

        Set<ConstraintViolation<PersonRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar violação quando e-mail é nulo")
    void nullEmail_Violation() {
        PersonRequestDto dto = new PersonRequestDto(null, "User Name", "password123");

        Set<ConstraintViolation<PersonRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("nmEmail"));
    }

    @Test
    @DisplayName("Deve retornar violação quando e-mail é inválido")
    void invalidEmail_Violation() {
        PersonRequestDto dto = new PersonRequestDto("not-an-email", "User Name", "password123");

        Set<ConstraintViolation<PersonRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("nmEmail"));
    }

    @Test
    @DisplayName("Deve retornar violação quando senha é nula")
    void nullPassword_Violation() {
        PersonRequestDto dto = new PersonRequestDto("user@email.com", "User Name", null);

        Set<ConstraintViolation<PersonRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("cdPassword"));
    }

    @Test
    @DisplayName("Deve permitir nmName nulo")
    void nullName_NoViolation() {
        PersonRequestDto dto = new PersonRequestDto("user@email.com", null, "password123");

        Set<ConstraintViolation<PersonRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve expor os campos corretamente via accessors do record")
    void recordAccessors() {
        PersonRequestDto dto = new PersonRequestDto("user@email.com", "User Name", "pass");

        assertThat(dto.nmEmail()).isEqualTo("user@email.com");
        assertThat(dto.nmName()).isEqualTo("User Name");
        assertThat(dto.cdPassword()).isEqualTo("pass");
    }

    @Test
    @DisplayName("Deve construir PersonRequestDto a partir de PersonModel")
    void constructFromPersonModel() {
        PersonModel model = new PersonModel("model@email.com", "Model Name", "modelpass");

        PersonRequestDto dto = new PersonRequestDto(model);

        assertThat(dto.nmEmail()).isEqualTo("model@email.com");
        assertThat(dto.nmName()).isEqualTo("Model Name");
        assertThat(dto.cdPassword()).isEqualTo("modelpass");
    }

    @Test
    @DisplayName("Dois records com mesmos valores devem ser iguais")
    void equality_SameValues() {
        PersonRequestDto d1 = new PersonRequestDto("user@email.com", "User", "pass");
        PersonRequestDto d2 = new PersonRequestDto("user@email.com", "User", "pass");

        assertThat(d1).isEqualTo(d2);
        assertThat(d1.hashCode()).isEqualTo(d2.hashCode());
    }

    @Test
    @DisplayName("Dois records com valores diferentes devem ser diferentes")
    void equality_DifferentValues() {
        PersonRequestDto d1 = new PersonRequestDto("a@email.com", "User A", "pass1");
        PersonRequestDto d2 = new PersonRequestDto("b@email.com", "User B", "pass2");

        assertThat(d1).isNotEqualTo(d2);
    }
}
