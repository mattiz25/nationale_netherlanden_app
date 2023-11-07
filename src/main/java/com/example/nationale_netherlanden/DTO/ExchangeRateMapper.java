package com.example.nationale_netherlanden.DTO;

import com.example.nationale_netherlanden.Account;
import com.example.nationale_netherlanden.ExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ExchangeRateMapper {

    ExchangeRate exchangeRateToExchangeRateDto(ExchangeRate exchangeRate);

    ExchangeRateDto exchangeRateDtoToExchangeRate(ExchangeRateDto exchangeRateDto);

}
