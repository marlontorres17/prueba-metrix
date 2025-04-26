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

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ContactController {

    private final CreateContactUseCase createContactUseCase;
    private final GetDailySubmissionsUseCase getDailySubmissionsUseCase;

    @PostMapping("/api/contact-submissions")
    public ResponseEntity<ApiResponse<Object>> submitContactForm(@Valid @RequestBody CreateContactCommand command) {
        createContactUseCase.execute(command);
        return ResponseEntity
                .status(ApiCode.CREATED.getHttpStatus())
                .body(ApiResponse.success(null, "Formulario enviado exitosamente", ApiCode.CREATED.getCode()));
    }

    @GetMapping("/api/metrics/daily-submissions")
    public ResponseEntity<ApiResponse<Long>> getDailySubmissions() {
        long dailySubmissions = getDailySubmissionsUseCase.execute();
        return ResponseEntity
                .status(ApiCode.OK.getHttpStatus())
                .body(ApiResponse.success(dailySubmissions, "Consulta de formularios diarios exitosa",
                        ApiCode.OK.getCode()));
    }

}
