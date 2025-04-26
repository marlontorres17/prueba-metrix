package com.metrix.contacto.contacto.application.interfaces;

import java.time.LocalDate;
import java.util.Map;

import com.metrix.contacto.contacto.domain.entity.ContactEntity;

public interface IContactService {
    void save(ContactEntity entity);

    Map<String, Long> countContactsByCountry();

    long countContactsByDateRange(LocalDate startDate, LocalDate endDate);
}