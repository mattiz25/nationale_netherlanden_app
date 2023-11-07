package com.example.nationale_netherlanden;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Currency {
    PLN("PLN"),
    USD("USD");

    private final String value;

    Currency(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    public static Currency fromString(String value) {
        for (Currency currency : values()) {
            if (currency.value.equals(value)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Invalid currency: " + value);
    }
}
