package com.pgr301.exam;

import com.pgr301.exam.model.Account;
import com.pgr301.exam.model.Transaction;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Metrics;

import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

import static java.math.BigDecimal.*;
import static java.util.Optional.ofNullable;

@RestController
public class BankAccountController implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private BankingCoreSystmeService bankService;




    @PostMapping(path = "/account/{fromAccount}/transfer/{toAccount}", consumes = "application/json", produces = "application/json")
    public void transfer(@RequestBody Transaction tx, @PathVariable String fromAccount, @PathVariable String toAccount) {
        long start = System.currentTimeMillis();
        bankService.transfer(tx, fromAccount, toAccount);
        Metrics.timer("post_transfer", "duration", String.valueOf(valueOf(System.currentTimeMillis()-start))).record(System.currentTimeMillis()-start, TimeUnit.MILLISECONDS);

    }
    @Timed("Pal2")
    @PostMapping(path = "/account", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Account> updateAccount(@RequestBody Account a) {
        long start = System.currentTimeMillis();
        bankService.updateAccount(a);
        Metrics.timer("postAccount", "duration", String.valueOf(valueOf(System.currentTimeMillis()-start))).record(System.currentTimeMillis()-start, TimeUnit.MILLISECONDS);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    @Timed("pal")
    @GetMapping(path = "/account/{accountId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Account> balance(@PathVariable String accountId) {
        long start = System.currentTimeMillis();
        Account account = ofNullable(bankService.getAccount(accountId)).orElseThrow(AccountNotFoundException::new);
        Metrics.timer("getAccountas", "duration", String.valueOf(valueOf(System.currentTimeMillis()-start))).record(System.currentTimeMillis()-start, TimeUnit.MILLISECONDS);
        return new ResponseEntity<>(account, HttpStatus.OK);

//
//        try{
//            Account account = ofNullable(bankService.getAccount(accountId)).orElseThrow(AccountNotFoundException::new);
//            Metrics.timer("getAccount", "duration", String.valueOf(valueOf(System.currentTimeMillis()-start))).record(System.currentTimeMillis()-start, TimeUnit.MILLISECONDS);
//            return new ResponseEntity<>(account, HttpStatus.OK);
//        }catch (BackEndException b){
//            Metrics.timer("getAccount", "duration", String.valueOf(valueOf(System.currentTimeMillis()-start))).record(System.currentTimeMillis()-start, TimeUnit.MILLISECONDS);
//            throw new BackEndException();
//        }

    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "video not found")
    public static class AccountNotFoundException extends RuntimeException {
    }
}

