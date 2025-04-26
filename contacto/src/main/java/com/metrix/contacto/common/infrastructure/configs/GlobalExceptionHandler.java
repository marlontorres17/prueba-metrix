package com.metrix.contacto.common.infrastructure.configs;

import com.metrix.contacto.common.application.dto.ApiResponse;
import com.metrix.contacto.common.infrastructure.exceptions.ValidationError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<ValidationError>>> handleValidationException(MethodArgumentNotValidException ex) {
        List<ValidationError> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> new ValidationError(err.getField(), err.getDefaultMessage()))
                .collect(Collectors.toList());

                return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                    errors,
                    "Validación fallida: Hay campos con errores.",
                    HttpStatus.BAD_REQUEST.value(),
                    "VALIDATION_ERROR"
                ));
    }
}