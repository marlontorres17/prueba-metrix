package com.metrix.contacto.contacto.application.useCase;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.metrix.contacto.contacto.application.interfaces.IContactService;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class GetSubmissionsByCountryUseCase {

    private final IContactService contactService;

    public Map<String, Long> execute() {
        return contactService.countContactsByCountry();
    }
}