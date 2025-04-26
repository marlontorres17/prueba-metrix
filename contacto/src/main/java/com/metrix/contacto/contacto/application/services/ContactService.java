package com.metrix.contacto.contacto.application.services;


import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.metrix.contacto.common.infrastructure.exceptions.NotFoundException;
import com.metrix.contacto.contacto.application.interfaces.IContactService;
import com.metrix.contacto.contacto.application.proyections.CountryCount;
import com.metrix.contacto.contacto.domain.entity.ContactEntity;
import com.metrix.contacto.contacto.infrastructure.repository.ContactRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;

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

    @Override
    public Map<String, Long> countContactsByCountry() {
        return contactRepository.countGroupedByCountry()
                .stream()
                .collect(Collectors.toMap(CountryCount::getCountry, CountryCount::getCount));
    }

    @Override
    public long countContactsByDateRange(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        long count = contactRepository.countContactsBetweenDates(startDateTime, endDateTime);

        if (count == 0) {
            throw new NotFoundException("No se encontraron registros en el rango de fechas indicado", "NOT_FOUND");
        }

        return count;
    }

}