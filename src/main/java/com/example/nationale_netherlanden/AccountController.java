package com.example.nationale_netherlanden;

import com.example.nationale_netherlanden.DTO.AccountDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.Map;

public interface AccountController {
    public ResponseEntity<Account> createAccount(@RequestBody @Valid AccountRequest accountRequest);

    public ResponseEntity<Account> getAccount(@PathVariable String accountUid);

    public ResponseEntity<Map<String, Double>> getAccountBalance(@PathVariable String accountUid) throws IOException;

    public Map<String, Double> exchangeCurrency(@PathVariable String accountUid, @RequestBody @Valid ExchangeRequest
            exchangeRequest) throws IOException;

}