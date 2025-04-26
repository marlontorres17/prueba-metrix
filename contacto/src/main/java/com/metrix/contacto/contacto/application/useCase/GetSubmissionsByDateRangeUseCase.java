package com.metrix.contacto.contacto.application.useCase;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.metrix.contacto.contacto.application.interfaces.IContactService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetSubmissionsByDateRangeUseCase {

    private final IContactService contactService;

    public long execute(LocalDate start, LocalDate end) {
        return contactService.countContactsByDateRange(start, end);
    }
}
