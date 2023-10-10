package com.mgmetehan.accountservice.controller;

import com.mgmetehan.accountservice.dto.CreateAccountDto;
import com.mgmetehan.accountservice.model.Account;
import com.mgmetehan.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public Account createAccount(@RequestBody CreateAccountDto account) {
        return accountService.createAccount(account);
    }

    @GetMapping
    public List<Account> getAccounts() {
        return accountService.findAll();
    }
}
