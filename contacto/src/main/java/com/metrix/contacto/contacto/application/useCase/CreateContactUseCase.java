package com.metrix.contacto.contacto.application.useCase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.metrix.contacto.contacto.application.command.CreateContactCommand;
import com.metrix.contacto.contacto.application.interfaces.IContactService;
import com.metrix.contacto.contacto.application.interfaces.IEmailService;
import com.metrix.contacto.contacto.application.factory.ContactFactory;
import com.metrix.contacto.common.infrastructure.utils.PhoneFormatter;
import com.metrix.contacto.contacto.application.builder.EmailMessageBuilder;
import com.metrix.contacto.contacto.application.builder.EmailMessage;
import com.metrix.contacto.contacto.domain.entity.ContactEntity;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateContactUseCase {

    private final IContactService contactService;
    private final IEmailService emailService;
    private final PhoneFormatter phoneFormatter;
    private final ContactFactory contactFactory;
    private final EmailMessageBuilder emailMessageBuilder;

    @Transactional
    public void execute(@Valid CreateContactCommand command) {
        String formattedPhone = phoneFormatter.format(command.getCountry(), command.getPhone());
        ContactEntity entity = contactFactory.createFromCommand(command, formattedPhone);

        contactService.save(entity);

        EmailMessage userEmail = emailMessageBuilder.buildUserEmail(entity);
        EmailMessage adminEmail = emailMessageBuilder.buildAdminNotification(entity);

        emailService.sendEmail(userEmail.getTo(), userEmail.getSubject(), userEmail.getBody());
        emailService.sendEmail(adminEmail.getTo(), adminEmail.getSubject(), adminEmail.getBody());
    }
}