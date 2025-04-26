package com.metrix.contacto.common.infrastructure.utils;

import com.metrix.contacto.common.infrastructure.loaders.CountryPhonePrefixLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneFormatter {

    private final CountryPhonePrefixLoader prefixLoader;

    public String format(String country, String phone) {
        String prefix = prefixLoader.findPrefixByCountry(country);
        return (prefix != null) ? prefix + phone : phone;
    }
}