package com.metrix.contacto.contacto.application.useCase;

import com.metrix.contacto.contacto.application.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetDailySubmissionsUseCase {

    private final ContactService contactService;

    public long execute() {
        return contactService.countContactsForToday();
    }
}