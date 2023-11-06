package com.example.nationale_netherlanden.DTO;

import com.example.nationale_netherlanden.Account;
import com.example.nationale_netherlanden.ExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExchangeRateMapper {
    ExchangeRateMapper INSTANCE = Mappers.getMapper(ExchangeRateMapper.class);

    ExchangeRate exchangeRateToExchangeRateDto(ExchangeRate exchangeRate);

    ExchangeRateDto exchangeRateDtoToExchangeRate(ExchangeRateDto exchangeRateDto);

}
