package com.metrix.contacto.contacto.infrastructure.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.metrix.contacto.contacto.application.proyections.CountryCount;
import com.metrix.contacto.contacto.domain.entity.ContactEntity;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Long> {
    long countByCreatedAtBetween(java.time.LocalDateTime start, java.time.LocalDateTime end);

    @Query("SELECT COUNT(c) FROM ContactEntity c WHERE DATE(c.createdAt) = :today")
    long countContactsByDate(LocalDate today);

    @Query("SELECT c.country AS country, COUNT(c) AS count FROM ContactEntity c GROUP BY c.country")
    List<CountryCount> countGroupedByCountry();

   @Query("SELECT COUNT(c) FROM ContactEntity c WHERE c.createdAt BETWEEN :startDate AND :endDate")
   long countContactsBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}