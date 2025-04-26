package com.metrix.contacto.contacto.infrastructure.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.metrix.contacto.common.application.dto.ApiResponse;
import com.metrix.contacto.common.infrastructure.codes.ApiCode;
import com.metrix.contacto.contacto.application.command.CreateContactCommand;
import com.metrix.contacto.contacto.application.useCase.CreateContactUseCase;
import com.metrix.contacto.contacto.application.useCase.GetDailySubmissionsUseCase;
import com.metrix.contacto.contacto.application.useCase.GetSubmissionsByCountryUseCase;
import com.metrix.contacto.contacto.application.useCase.GetSubmissionsByDateRangeUseCase;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ContactController {

    private final CreateContactUseCase createContactUseCase;
    private final GetDailySubmissionsUseCase getDailySubmissionsUseCase;
    private final GetSubmissionsByCountryUseCase getSubmissionsByCountryUseCase;
    private final GetSubmissionsByDateRangeUseCase getSubmissionsByDateRangeUseCase;

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

    @Operation(summary = "Consulta de formularios enviados por país", description = """
            Devuelve un listado con la cantidad de formularios de contacto enviados agrupados por país.

            **Escenario de uso:**
            Un administrador desea visualizar desde qué países los usuarios han intentado comunicarse.

            **Proceso:**
            - Agrupa los envíos de formularios según el país de origen.
            - Devuelve un mapa donde la clave es el nombre del país y el valor es el número de formularios enviados.

            **Respuesta esperada:**
            - `200 OK`: Mapa de países con la cantidad de formularios enviados.
            - `500 Internal Server Error`: Fallo al obtener la información desde la base de datos.
            """)
    @GetMapping("/api/metrics/submissions-by-country")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getSubmissionsByCountry() {
        Map<String, Long> result = getSubmissionsByCountryUseCase.execute();
        return ResponseEntity
                .status(ApiCode.OK.getHttpStatus())
                .body(ApiResponse.success(result, "Consulta de formularios por país exitosa",
                        ApiCode.OK.getCode()));
    }

    @Operation(summary = "Consulta de formularios enviados en un rango de fechas", description = """
            Devuelve la cantidad de formularios de contacto enviados entre dos fechas específicas.

            **Formato de fechas esperado:**
            - `start`: Fecha de inicio en formato `yyyy-MM-dd` (ejemplo: 2025-04-25)
            - `end`: Fecha de fin en formato `yyyy-MM-dd` (ejemplo: 2025-04-26)

            **Escenario de uso:**
            Un administrador desea conocer cuántos formularios fueron enviados en un período determinado.

            **Proceso:**
            - Filtra los envíos de formularios entre la fecha de inicio y la fecha de fin, ambas inclusive.
            - Devuelve el total de formularios enviados en ese rango.

            **Respuesta esperada:**
            - `200 OK`: Número total de formularios enviados en el rango indicado.
            - `400 Bad Request`: Formato incorrecto de fechas o parámetros faltantes.
            - `500 Internal Server Error`: Fallo al obtener la información desde la base de datos.
            """)
    @GetMapping("/api/metrics/submissions-by-date-range")
    public ResponseEntity<ApiResponse<Long>> getSubmissionsByDateRange(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        long result = getSubmissionsByDateRangeUseCase.execute(start, end);
        return ResponseEntity
                .status(ApiCode.OK.getHttpStatus())
                .body(ApiResponse.success(result, "Consulta de formularios por rango de fechas exitosa",
                        ApiCode.OK.getCode()));
    }


}
