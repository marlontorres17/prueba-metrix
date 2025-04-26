package com.metrix.contacto.common.infrastructure.configs;

import com.metrix.contacto.common.application.dto.ApiResponse;
import com.metrix.contacto.common.infrastructure.enums.ApiErrorCode;
import com.metrix.contacto.common.infrastructure.exceptions.NotFoundException;
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
                    ApiErrorCode.VALIDATION_ERROR.getCode()
                ));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataNotFoundException(NotFoundException ex) {
        // Si necesitas un cuerpo con detalles adicionales, como un objeto de error, lo puedes incluir aquí
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(
                        ex.getMessage(),
                        "No se encontraron registros para el rango de fechas solicitado",
                        HttpStatus.NOT_FOUND.value(),
                        ApiErrorCode.NOT_FOUND.getCode()
                ));
    }
}