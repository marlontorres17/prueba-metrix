package com.metrix.contacto.contacto.application.services;


import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.metrix.contacto.contacto.application.interfaces.IContactService;
import com.metrix.contacto.contacto.domain.entity.ContactEntity;
import com.metrix.contacto.contacto.infrastructure.repository.ContactRepository;

@Service
@RequiredArgsConstructor
public class ContactService implements IContactService {

    private final ContactRepository contactRepository;

    @Override
    public void save(ContactEntity entity) {
        contactRepository.save(entity);
    }

    public long countContactsForToday() {
        return contactRepository.countContactsByDate(LocalDate.now());
    }
}