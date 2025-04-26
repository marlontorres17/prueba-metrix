package com.metrix.contacto.common.infrastructure.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApiErrorCode {
    VALIDATION_ERROR("VALIDATION_ERROR"),
    NOT_FOUND("NOT_FOUND"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR");

    private final String code;

}
