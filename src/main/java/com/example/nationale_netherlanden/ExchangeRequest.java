package com.example.nationale_netherlanden;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRequest {

    @NotBlank(message = "Currency is Required")
    private String fromCurrency;
    @Positive(message = "Ammount must be a positive number")
    private double amount;

}
