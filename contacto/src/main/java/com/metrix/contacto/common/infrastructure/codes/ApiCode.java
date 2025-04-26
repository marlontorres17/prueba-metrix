package com.metrix.contacto.common.infrastructure.codes;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiCode {
    OK(HttpStatus.OK, 200),
    CREATED(HttpStatus.CREATED, 201),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400),
    NOT_FOUND(HttpStatus.NOT_FOUND, 404),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500);

    private final HttpStatus httpStatus;
    private final int code;

    ApiCode(HttpStatus httpStatus, int code) {
        this.httpStatus = httpStatus;
        this.code = code;
    }
}
