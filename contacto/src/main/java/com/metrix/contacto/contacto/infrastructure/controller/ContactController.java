package com.metrix.contacto.contacto.infrastructure.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.metrix.contacto.common.application.dto.ApiResponse;
import com.metrix.contacto.common.infrastructure.codes.ApiCode;
import com.metrix.contacto.contacto.application.command.CreateContactCommand;
import com.metrix.contacto.contacto.application.useCase.CreateContactUseCase;
import com.metrix.contacto.contacto.application.useCase.GetDailySubmissionsUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ContactController {

    private final CreateContactUseCase createContactUseCase;
    private final GetDailySubmissionsUseCase getDailySubmissionsUseCase;

    @Operation(
    summary = "Envía el formulario de contacto",
    description = """
        Permite a los usuarios enviar su información de contacto junto con un mensaje.

        **Escenario de uso:**  
        Un visitante desea comunicarse con la empresa o dejar un comentario desde la plataforma web.

        **Proceso:**  
        - Valida automáticamente los campos del formulario.  
        - Almacena la información en la base de datos.  
        - Puede generar una notificación o acción adicional en el sistema.

        **Respuesta esperada:**  
        - `201 Created`: El formulario fue enviado exitosamente.  
        - `400 Bad Request`: Error de validación en los campos.  
        - `500 Internal Server Error`: Fallo inesperado durante el procesamiento.
        """
    )
    @PostMapping("/api/contact-submissions")
    public ResponseEntity<ApiResponse<Object>> submitContactForm(@Valid @RequestBody CreateContactCommand command) {
        createContactUseCase.execute(command);
        return ResponseEntity
                .status(ApiCode.CREATED.getHttpStatus())
                .body(ApiResponse.success(null, "Formulario enviado exitosamente", ApiCode.CREATED.getCode()));
    }

    @Operation(
    summary = "Consulta diaria de formularios enviados",
    description = """
        Devuelve la cantidad de formularios de contacto enviados en el día actual.

        **Escenario de uso:**  
        Un administrador desea conocer cuántos usuarios han intentado comunicarse durante el día.

        **Proceso:**  
        - Consulta la base de datos para contar los envíos realizados hoy.  
        - Devuelve el total como un número entero.

        **Respuesta esperada:**  
        - `200 OK`: Número total de formularios enviados en el día.  
        - `500 Internal Server Error`: Fallo al obtener la información desde la base de datos.
        """
    )
    @GetMapping("/api/metrics/daily-submissions")
    public ResponseEntity<ApiResponse<Long>> getDailySubmissions() {
        long dailySubmissions = getDailySubmissionsUseCase.execute();
        return ResponseEntity
                .status(ApiCode.OK.getHttpStatus())
                .body(ApiResponse.success(dailySubmissions, "Consulta de formularios diarios exitosa",
                        ApiCode.OK.getCode()));
    }

}
