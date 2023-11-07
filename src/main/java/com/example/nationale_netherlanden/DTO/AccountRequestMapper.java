package com.example.nationale_netherlanden.DTO;

import com.example.nationale_netherlanden.AccountRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountRequestMapper {

    AccountRequest accountRequestDtoToAccountRequest(AccountRequestDto accountRequestDto);

    AccountRequestDto accountRequestToAccountRequestDto(AccountRequest accountRequest);

}
