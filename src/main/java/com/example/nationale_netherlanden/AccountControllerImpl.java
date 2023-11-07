package com.example.nationale_netherlanden;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    public ResponseEntity<Account> createAccount(@RequestBody @Valid AccountRequest accountRequest) {
        logger.info("initialization of request CreateAccount");

        if (!findByAccountRequestInMap(accounts, accountRequest)) {
            String newAccountUid;
            do {
                newAccountUid = UUID.randomUUID().toString();
            }
            while (findIfAnySameUuidAlreadyExists(accounts, newAccountUid));


            Account account = new Account
                    (newAccountUid,
                            accountRequest.getFirstName(),
                            accountRequest.getLastName(),
                            accountRequest.getInitialBalancePLN());

            accounts.put(account.getAccountUid(), account);
            return ResponseEntity.ok(account);
        }
        return ResponseEntity.internalServerError().build();
    }


//        accountService.createAccount(accounts, new AccountRequestDto());


//        AccountRequestDto accountRequestDto = accountRequestMapper.accountRequestToAccountRequestDto(accountRequest);
//
//        return ResponseEntity.ok(accountService.createAccount(accounts, accountRequestDto));


    private boolean findByAccountRequestInMap(Map<String, Account> accounts, AccountRequest accountRequest) {
        return accounts.values().stream()
                .anyMatch(account -> account.getFirstName().equals(accountRequest.getFirstName()) &&
                        account.getBalancePLN() == accountRequest.getInitialBalancePLN() &&
                        account.getLastName().equals(accountRequest.getLastName()));

    }

    private boolean findIfAnySameUuidAlreadyExists(Map<String, Account> accounts, String accountUid) {
        return accounts.keySet().stream()
                .anyMatch(keyAccountUid -> accountUid.equals(keyAccountUid));
    }


    @GetMapping("/{accountUid}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountUid) {

        logger.info("request of retriving accounts from database with uuid ", accountUid);

        return ResponseEntity.ok(accounts.get(accountUid));
    }

    @GetMapping("/{accountUid}/balance")
    public ResponseEntity<Map<String, Double>> getAccountBalance(@PathVariable String accountUid) throws
            IOException {

        logger.info("request of retriving account Balance from database with uuid ", accountUid);

        Account account = accounts.get(accountUid);

        double plnToUsdExchangeRate = getPLNToUSDExchangeRate();
        double balanceUSD = account.getBalancePLN() / plnToUsdExchangeRate;

        Map<String, Double> balances = new HashMap<>();
        balances.put(Currency.PLN.toString(), account.getBalancePLN());
        balances.put(Currency.USD.toString(), balanceUSD);

        return ResponseEntity.ok(balances);
    }

    @PostMapping("/{accountUid}/exchange")
    public Map<String, Double> exchangeCurrency(@PathVariable String
                                                        accountUid, @RequestBody @Valid ExchangeRequest exchangeRequest) throws IOException {
        Account account = accounts.get(accountUid);
        double plnToUsdExchangeRate = getPLNToUSDExchangeRate();

        if (exchangeRequest.getFromCurrency().equals(Currency.PLN)) {
            if (account.getBalancePLN() >= exchangeRequest.getAmount()) {
                account.setBalancePLN(account.getBalancePLN() - exchangeRequest.getAmount());
                double amountUSD = exchangeRequest.getAmount() / plnToUsdExchangeRate;
                account.setBalancePLN(account.getBalancePLN() + amountUSD);
            }
        } else if (exchangeRequest.getFromCurrency().equals(Currency.USD)) {
            double amountPLN = exchangeRequest.getAmount() * plnToUsdExchangeRate;
            if (account.getBalancePLN() >= amountPLN) {
                account.setBalancePLN(account.getBalancePLN() - amountPLN);
                account.setBalancePLN(account.getBalancePLN() + exchangeRequest.getAmount());
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
