package com.metrix.contacto.contacto.application.interfaces;

public interface IEmailService {
    void sendEmail(String to, String subject, String body);
}
