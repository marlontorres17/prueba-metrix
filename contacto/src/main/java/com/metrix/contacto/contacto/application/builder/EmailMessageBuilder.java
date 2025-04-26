package com.metrix.contacto.contacto.application.builder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.metrix.contacto.contacto.domain.entity.ContactEntity;

@Component
public class EmailMessageBuilder {

    @Value("${contact.admin-email}")
    private String adminEmail;

    public EmailMessage buildUserEmail(ContactEntity entity) {
        String subject = "Formulario recibido";
        String body = "Hola " + entity.getFullName()
                + ",\n\nHemos recibido tu mensaje. Pronto nos pondremos en contacto contigo.\n\nSaludos,\nConsultores Estratégicos Ltda.";

        return new EmailMessage(entity.getEmail(), subject, body);
    }

    public EmailMessage buildAdminNotification(ContactEntity entity) {
        String subject = "Nuevo contacto recibido";
        String body = "Se ha recibido un nuevo mensaje de " + entity.getFullName() + " (" + entity.getEmail()
                + ").\n\nMensaje:\n" + entity.getMessage();

        return new EmailMessage(adminEmail, subject, body);
    }
}