package com.metrix.contacto.contacto.application.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateContactCommand {

    @Schema(name = "full_name", description = "Nombre completo del usuario", example = "Marlon Estiven Torres Medina")
    @NotBlank(message = "El nombre completo es obligatorio. Por favor, ingrese su nombre completo.")
    @Size(max = 100, message = "El nombre completo no debe exceder los 100 caracteres.")
    @Pattern(regexp = "^[A-Za-záéíóúÁÉÍÓÚüÜ ]+$", message = "El nombre completo solo puede contener letras y espacios.")
    private String fullName;

    @Schema(name = "email", description = "Correo electrónico del usuario", example = "torresmedina195@gmail.com")
    @NotBlank(message = "El correo electrónico es obligatorio. Por favor, ingrese un correo electrónico válido.")
    @Email(message = "El formato del correo electrónico es inválido. Asegúrese de que esté en el formato correcto.")
    @Size(max = 150, message = "El correo electrónico no debe exceder los 150 caracteres.")
    private String email;

    @Schema(name = "country", description = "País del usuario", example = "Colombia")
    @Size(max = 100, message = "El país no debe exceder los 100 caracteres.")
    private String country;

    @Schema(name = "phone", description = "Teléfono del usuario", example = "3133740261")
    @Pattern(regexp = "^[+]?[0-9]*[0-9]+$", message = "El teléfono debe contener solo números y, opcionalmente, un signo '+' al inicio.")
    @Size(max = 20, message = "El teléfono no debe exceder los 20 caracteres.")
    private String phone;

    @Schema(name = "message", description = "Mensaje del usuario", example = "¡Hola, tengo una consulta sobre su producto!")
    @NotBlank(message = "El mensaje es obligatorio. Por favor, ingrese el mensaje que desea enviar.")
    @Size(max = 500, message = "El mensaje no debe exceder los 500 caracteres.")
    private String message;
}
