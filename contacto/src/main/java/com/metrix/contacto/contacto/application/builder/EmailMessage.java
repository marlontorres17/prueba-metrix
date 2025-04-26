package com.metrix.contacto.contacto.application.builder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailMessage {
    private String to;
    private String subject;
    private String body;
}