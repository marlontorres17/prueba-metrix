package com.metrix.contacto.common.infrastructure.enums;

import java.util.Arrays;

public enum CountryPhonePrefix {
    COLOMBIA("Colombia", "+57"),
    MEXICO("México", "+52"),
    UNITED_STATES("Estados Unidos", "+1"),
    ARGENTINA("Argentina", "+54"),
    SPAIN("España", "+34");

    private final String countryName;
    private final String prefix;

    CountryPhonePrefix(String countryName, String prefix) {
        this.countryName = countryName;
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public static String findPrefixByCountry(String countryInput) {
        if (countryInput == null) {
            return null;
        }
        String normalizedInput = countryInput.trim().toLowerCase();
        return Arrays.stream(values())
                .filter(c -> c.countryName.toLowerCase().equals(normalizedInput))
                .map(CountryPhonePrefix::getPrefix)
                .findFirst()
                .orElse(null);
    }
}
