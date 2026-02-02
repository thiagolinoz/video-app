package br.com.fiap.videoapp.infraestructure.web.api.dtos;

import br.com.fiap.videoapp.domain.models.PersonModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record PersonRequestDto(
        @Valid
        @NotNull(message = "The e-mail is obligatory")
        @Email(message = "The e-mail is not a valid e-mail")
        String nmEmail,
        String nmName,
        @Valid
        @NotNull(message = "The password is obligatory")
        String cdPassword
) {
    public PersonRequestDto(PersonModel personModel) {
        this(personModel.getNmEmail(), personModel.getNmName(), personModel.getCdPassword());
    }
}
