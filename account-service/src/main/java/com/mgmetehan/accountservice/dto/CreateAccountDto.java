package com.mgmetehan.accountservice.dto;

import lombok.Data;

@Data
public class CreateAccountDto {
    private String username;
    private String mail;
    private String password;
}
