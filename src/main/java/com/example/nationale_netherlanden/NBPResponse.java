package com.example.nationale_netherlanden;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NBPResponse {
    private String currency;
    private String code;
    private ExchangeRate[] rates;

}
