package com.metrix.contacto.contacto.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "contact_submissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(length = 100)
    private String country;

    @Column(length = 20)
    private String phone;

    @Column(nullable = false, columnDefinition = "TEXT", length = 500)
    private String message;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}