package com.metrix.contacto.contacto.application.interfaces;

import com.metrix.contacto.contacto.domain.entity.ContactEntity;

public interface IContactService {
    void save(ContactEntity entity);
}