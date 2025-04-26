package com.metrix.contacto.contacto.application.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateContactCommand {

    @NotBlank(message = "El nombre completo es obligatorio.")
    private String fullName;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "Formato de correo inválido.")
    private String email;

    private String country;

    private String phone;

    @NotBlank(message = "El mensaje es obligatorio.")
    private String message;
}
