package com.mgmetehan.accountservice.model;

import com.mgmetehan.accountservice.model.enums.MailStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

import static java.time.LocalDateTime.now;

@Data
@Entity
@Table(name = "accounts")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String username;

    private String mail;

    private String password;

    private MailStatus mailStatus;

    @Column(name = "created_date")
    private Date createdDate;

    @PrePersist
    private void prePersist() {
        createdDate = Date.from(now().toInstant(java.time.ZoneOffset.UTC));
    }
}