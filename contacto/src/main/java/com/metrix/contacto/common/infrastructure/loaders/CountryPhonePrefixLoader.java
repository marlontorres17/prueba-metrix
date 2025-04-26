package com.metrix.contacto.common.infrastructure.loaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metrix.contacto.common.infrastructure.utils.StringNormalizer;

import jakarta.annotation.PostConstruct;

@Component
public class CountryPhonePrefixLoader {

    private Map<String, String> countryPrefixMap;

    @PostConstruct
    private void loadPrefixes() {
        try (InputStream inputStream = getClass().getResourceAsStream("/json/country-prefix.json")) {
            ObjectMapper mapper = new ObjectMapper();
            countryPrefixMap = mapper.readValue(inputStream, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error loading country-prefix.json", e);
        }
    }

    public String findPrefixByCountry(String countryInput) {
        if (countryInput == null) {
            return null;
        }

        String normalizedInput = StringNormalizer.normalize(countryInput);

        return countryPrefixMap.getOrDefault(normalizedInput, null);
    }
}
