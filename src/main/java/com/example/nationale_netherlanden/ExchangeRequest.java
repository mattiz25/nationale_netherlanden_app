package com.example.nationale_netherlanden;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Currency;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRequest {

    @NotNull(message = "Currency is Required")
    @Enumerated(EnumType.STRING)
    private Currency fromCurrency;
    @Positive(message = "Ammount must be a positive number")
    private double amount;

}
