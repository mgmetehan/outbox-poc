package com.mgmetehan.accountservice.repository;

import com.mgmetehan.accountservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
