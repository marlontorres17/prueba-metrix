package com.metrix.contacto.contacto.application.useCase;


import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.metrix.contacto.contacto.application.command.CreateContactCommand;
import com.metrix.contacto.contacto.application.interfaces.IContactService;
import com.metrix.contacto.contacto.domain.entity.ContactEntity;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CreateContactUseCase {

    private final IContactService contactService;
    
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
    }
}