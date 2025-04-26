package com.metrix.contacto.contacto.infrastructure.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.metrix.contacto.contacto.application.command.CreateContactCommand;
import com.metrix.contacto.contacto.application.useCase.CreateContactUseCase;

@RestController
@RequestMapping("/api/contact-submissions")
@RequiredArgsConstructor
public class ContactController {

    private final CreateContactUseCase createContactUseCase;

    @PostMapping
    public ResponseEntity<?> submitContactForm(@Valid @RequestBody CreateContactCommand command) {
        createContactUseCase.execute(command);
        return new ResponseEntity<>(new ApiResponse("Formulario recibido correctamente."), HttpStatus.CREATED);
    }

    // Clase de respuesta en español
    private static class ApiResponse {
        private final String mensaje;

        public ApiResponse(String mensaje) {
            this.mensaje = mensaje;
        }

        public String getMensaje() {
            return mensaje;
        }
    }
}
