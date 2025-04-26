package com.metrix.contacto.contacto.application.useCase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.metrix.contacto.common.infrastructure.enums.CountryPhonePrefix;
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

    /**
     * Procesa el formulario de contacto enviado por el usuario.
     *
     * <p>
     * Este método realiza las siguientes acciones:
     * </p>
     * <ul>
     * <li>Construye una entidad {@link ContactEntity} a partir de los datos
     * recibidos en el comando.</li>
     * <li>Almacena la información en la base de datos mediante
     * {@link contactService#save}.</li>
     * <li>Envía un correo electrónico de confirmación al usuario, indicando que su
     * mensaje fue recibido.</li>
     * <li>Envía una notificación al administrador con los detalles del nuevo
     * contacto recibido.</li>
     * </ul>
     *
     * @param command Objeto que contiene los datos del formulario de contacto. Debe
     *                estar validado previamente.
     * @throws jakarta.validation.ConstraintViolationException si los datos del
     *                                                         comando no cumplen
     *                                                         las validaciones.
     */
    @Transactional
    public void execute(@Valid CreateContactCommand command) {
        String prefix = CountryPhonePrefix.findPrefixByCountry(command.getCountry());

        String formattedPhone = (prefix != null) ? prefix + command.getPhone() : command.getPhone();

        ContactEntity entity = ContactEntity.builder()
                .fullName(command.getFullName())
                .email(command.getEmail())
                .country(command.getCountry())
                .phone(formattedPhone)
                .message(command.getMessage())
                .createdAt(LocalDateTime.now())
                .build();

        contactService.save(entity);

        // Email al usuario
        String userSubject = "Formulario recibido";
        String userBody = "Hola " + entity.getFullName()
                + ",\n\nHemos recibido tu mensaje. Pronto nos pondremos en contacto contigo.\n\nSaludos,\nConsultores Estratégicos Ltda.";
        emailService.sendEmail(entity.getEmail(), userSubject, userBody);

        // Email al administrador
        String adminSubject = "Nuevo contacto recibido";
        String adminBody = "Se ha recibido un nuevo mensaje de " + entity.getFullName() + " (" + entity.getEmail()
                + ").\n\nMensaje:\n" + entity.getMessage();
        emailService.sendEmail(adminEmail, adminSubject, adminBody);
    }

}