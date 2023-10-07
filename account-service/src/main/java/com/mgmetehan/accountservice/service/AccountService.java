package com.mgmetehan.accountservice.service;

import com.mgmetehan.accountservice.converter.OutboxConverter;
import com.mgmetehan.accountservice.dto.CreateAccountDto;
import com.mgmetehan.accountservice.model.Account;
import com.mgmetehan.accountservice.model.enums.MailStatus;
import com.mgmetehan.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final OutboxService outboxService;

    public Account createAccount(CreateAccountDto account) {
        Account newAccount = new Account();
        newAccount.setUsername(account.getUsername());
        newAccount.setMail(account.getMail());
        newAccount.setPassword(account.getPassword());
        newAccount.setMailStatus(MailStatus.CREATED);
        log.info("Account created: {}", newAccount);
        outboxService.createOutbox(OutboxConverter.convertToOutbox(newAccount));
        log.info("Outbox created");
        return accountRepository.save(newAccount);
    }
}
