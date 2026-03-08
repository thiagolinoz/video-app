package br.com.fiap.videoapp.infraestructure.web.api.controllers;

import br.com.fiap.videoapp.domain.models.PersonModel;
import br.com.fiap.videoapp.domain.ports.in.PersonServicePort;
import br.com.fiap.videoapp.infraestructure.web.api.dtos.PersonRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonServicePort personServicePort;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve criar pessoa e retornar 201 Created")
    void createPerson_Returns201() throws Exception {
        PersonRequestDto requestDto = new PersonRequestDto("test@email.com", "Test User", "password123");
        PersonModel personModel = new PersonModel("test@email.com", "Test User", "password123");

        when(personServicePort.createPerson(any(PersonModel.class))).thenReturn(personModel);

        mockMvc.perform(post("/api/v1/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/user/new"));

        verify(personServicePort, times(1)).createPerson(any(PersonModel.class));
    }

    @Test
    @DisplayName("Deve retornar 400 quando e-mail inválido")
    void createPerson_InvalidEmail_Returns400() throws Exception {
        PersonRequestDto requestDto = new PersonRequestDto("invalid-email", "Test User", "password123");

        mockMvc.perform(post("/api/v1/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 400 quando e-mail é nulo")
    void createPerson_NullEmail_Returns400() throws Exception {
        PersonRequestDto requestDto = new PersonRequestDto(null, "Test User", "password123");

        mockMvc.perform(post("/api/v1/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 400 quando senha é nula")
    void createPerson_NullPassword_Returns400() throws Exception {
        PersonRequestDto requestDto = new PersonRequestDto("test@email.com", "Test User", null);

        mockMvc.perform(post("/api/v1/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }
}
