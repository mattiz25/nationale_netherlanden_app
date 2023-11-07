package com.example.nationale_netherlanden;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.nationale_netherlanden.DTO.AccountDto;
import com.example.nationale_netherlanden.DTO.AccountMapper;
import com.example.nationale_netherlanden.DTO.AccountRequestDto;
import com.example.nationale_netherlanden.DTO.AccountRequestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountControllerImpl implements AccountController {

    //TODO CHANGE TO A DATABASE INSTEAD OF JAVA_COLLECTION_'MEMORY'
    private Map<String, Account> accounts = new HashMap<>();

    //TODO CHANGE TO A DATABASE INSTEAD OF JAVA_COLLECTION_'MEMORY'

    private RestTemplate restTemplate = new RestTemplate();
    private final String NBP_API_URL = "http://api.nbp.pl/api/exchangerates/rates/A/USD";

    private static final Logger logger = LogManager.getLogger(AccountControllerImpl.class);

    @Autowired
    private final AccountService accountService;

    @Autowired
    private final AccountMapper accountMapper;

    @Autowired
    private final AccountRequestMapper accountRequestMapper;


//TODO
    // all code should be invoked from ServiceLayer, and using Repository Data Structure with selected database
    //and implemented Transactional methods


    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody @Valid AccountRequest accountRequest) {

//        accountService.createAccount(accounts, new AccountRequestDto());

        logger.info("initialization of request CreateAccount");

//        AccountRequestDto accountRequestDto = accountRequestMapper.accountRequestToAccountRequestDto(accountRequest);
//
//        return ResponseEntity.ok(accountService.createAccount(accounts, accountRequestDto));

        Account account = new Account
                (accountRequest.getFirstName(),
                        accountRequest.getLastName(),
                        accountRequest.getInitialBalancePLN());
        accounts.put(account.getAccountUid(), account);
        return ResponseEntity.ok(accountMapper.accountToAccountDto(account));

    }


    @GetMapping("/{accountUid}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountUid) {

        logger.info("request of retriving accounts from database with uuid ", accountUid);

        return ResponseEntity.ok(accounts.get(accountUid));
    }

    @GetMapping("/{accountUid}/balance")
    public ResponseEntity<Map<String, Double>> getAccountBalance(@PathVariable String accountUid) throws IOException {

        logger.info("request of retriving account Balance from database with uuid ", accountUid);

        Account account = accounts.get(accountUid);

        double plnToUsdExchangeRate = getPLNToUSDExchangeRate();
        double balanceUSD = account.getBalancePLN() / plnToUsdExchangeRate;

        Map<String, Double> balances = new HashMap<>();
        balances.put("PLN", account.getBalancePLN());
        balances.put("USD", balanceUSD);

        return ResponseEntity.ok(balances);
    }

    @PostMapping("/{accountUid}/exchange")
    public Map<String, Double> exchangeCurrency(@PathVariable String accountUid, @RequestBody @Valid ExchangeRequest exchangeRequest) throws IOException {
        Account account = accounts.get(accountUid);
        double plnToUsdExchangeRate = getPLNToUSDExchangeRate();

        if (exchangeRequest.getFromCurrency().equalsIgnoreCase("PLN")) {
            if (account.getBalancePLN() >= exchangeRequest.getAmount()) {
                account.balancePLN -= exchangeRequest.getAmount();
                double amountUSD = exchangeRequest.getAmount() / plnToUsdExchangeRate;
                account.balancePLN += amountUSD;
            }
        } else if (exchangeRequest.getFromCurrency().equalsIgnoreCase("USD")) {
            double amountPLN = exchangeRequest.getAmount() * plnToUsdExchangeRate;
            if (account.getBalancePLN() >= amountPLN) {
                account.balancePLN -= amountPLN;
                account.balancePLN += exchangeRequest.getAmount();
            }
        }

        return getAccountBalance(accountUid).getBody();
    }

    private double getPLNToUSDExchangeRate() throws IOException {
        NBPResponse response = restTemplate.getForObject(NBP_API_URL, NBPResponse.class);
        if (response != null) {
            return response.getRates()[0].getMid();
        }
        return 0.0;
    }


}
