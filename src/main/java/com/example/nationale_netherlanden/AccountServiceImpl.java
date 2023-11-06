package com.example.nationale_netherlanden;

import com.example.nationale_netherlanden.DTO.AccountDto;
import com.example.nationale_netherlanden.DTO.AccountRequestDto;
import com.example.nationale_netherlanden.DTO.AccountRequestMapper;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {

    AccountRequestMapper accountRequestMapper;

    public AccountDto createAccount(Map<String, Account> accounts, AccountRequestDto accountRequestDto) {

        AccountRequest accountRequest = accountRequestMapper.accountRequestDtoToAccountRequest(accountRequestDto);

        Account account = new Account(accountRequest.getFirstName(),
                accountRequest.getLastName(),
                accountRequest.getInitialBalancePLN());
        accounts.put(account.getAccountUid(), account);

        AccountDto accountDto = new AccountDto();
        accountDto.setAccountUid(account.getAccountUid());
        accountDto.setLastName(account.getLastName());
        accountDto.setBalancePLN(account.getBalancePLN());

        return accountDto;
    }

//    public Map<String, Double> getAccountBalance(String accountUid, Map<String, Account> accounts) {
//    }
//
//    public Account getAccount(String accountUid) {
//        return accounts.get(accountUid);
//    }
//
//    Map<String, Double> getAccountBalance(String accountUid) throws IOException {
//
//    }
//
//    exchangeCurrency(String accountUid, ExchangeRequest exchangeRequest) throws IOException {
//        Account account = accounts.get(accountUid);
//    }
}


//TODO
//extract all of the methods from AccountController with mapping ,
// exporting and importing thorough the Service Layer only using DTOs structure