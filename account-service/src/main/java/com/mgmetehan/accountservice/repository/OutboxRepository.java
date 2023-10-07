package com.mgmetehan.accountservice.repository;

import com.mgmetehan.accountservice.model.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepository extends JpaRepository<Outbox, String> {
}
