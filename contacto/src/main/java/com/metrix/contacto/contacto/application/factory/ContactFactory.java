package com.metrix.contacto.contacto.application.factory;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.metrix.contacto.contacto.application.command.CreateContactCommand;
import com.metrix.contacto.contacto.domain.entity.ContactEntity;

@Component
public class ContactFactory {

    public ContactEntity createFromCommand(CreateContactCommand command, String formattedPhone) {
        return ContactEntity.builder()
                .fullName(command.getFullName())
                .email(command.getEmail())
                .country(command.getCountry())
                .phone(formattedPhone)
                .message(command.getMessage())
                .createdAt(LocalDateTime.now())
                .build();
    }
}