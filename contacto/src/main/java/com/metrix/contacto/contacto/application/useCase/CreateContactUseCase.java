package com.metrix.contacto.contacto.application.useCase;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.metrix.contacto.contacto.application.command.CreateContactCommand;
import com.metrix.contacto.contacto.application.interfaces.IContactService;
import com.metrix.contacto.contacto.application.interfaces.IEmailService;
import com.metrix.contacto.contacto.domain.entity.ContactEntity;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CreateContactUseCase {

    private final IContactService contactService;
    private final IEmailService emailService;

    @Value("${contact.admin-email}")
    private String adminEmail;
    
    @Transactional
    public void execute(@Valid CreateContactCommand command) {
        ContactEntity entity = ContactEntity.builder()
                .fullName(command.getFullName())
                .email(command.getEmail())
                .country(command.getCountry())
                .phone(command.getPhone())
                .message(command.getMessage())
                .createdAt(LocalDateTime.now())
                .build();

        contactService.save(entity);

        // Email al usuario
        String userSubject = "Formulario recibido";
        String userBody = "Hola " + entity.getFullName() + ",\n\nHemos recibido tu mensaje. Pronto nos pondremos en contacto contigo.\n\nSaludos,\nConsultores Estratégicos Ltda.";
        emailService.sendEmail(entity.getEmail(), userSubject, userBody);

        // Email al admin
        String adminSubject = "Nuevo contacto recibido";
        String adminBody = "Se ha recibido un nuevo mensaje de " + entity.getFullName() + " (" + entity.getEmail() + ").\n\nMensaje:\n" + entity.getMessage();
        emailService.sendEmail(adminEmail, adminSubject, adminBody);
    }
}