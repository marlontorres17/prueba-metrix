package com.metrix.contacto.contacto.infrastructure.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.metrix.contacto.contacto.domain.entity.ContactEntity;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Long> {
    long countByCreatedAtBetween(java.time.LocalDateTime start, java.time.LocalDateTime end);

    @Query("SELECT COUNT(c) FROM ContactEntity c WHERE DATE(c.createdAt) = :today")
    long countContactsByDate(LocalDate today);
}