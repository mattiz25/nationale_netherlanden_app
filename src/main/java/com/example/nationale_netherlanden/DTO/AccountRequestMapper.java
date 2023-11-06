package com.example.nationale_netherlanden.DTO;

import com.example.nationale_netherlanden.AccountRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountRequestMapper {
    AccountRequestMapper INSTANCE = Mappers.getMapper(AccountRequestMapper.class);


    AccountRequest accountRequestDtoToAccountRequest(AccountRequestDto accountRequestDto);

    AccountRequestDto accountRequestToAccountRequestDto(AccountRequest accountRequest);

}
