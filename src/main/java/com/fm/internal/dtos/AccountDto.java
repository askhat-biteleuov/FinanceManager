package com.fm.internal.dtos;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

public class AccountDto {
    @NotEmpty
    private String name;

    @Pattern(regexp = "^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\\.[0-9]{2})?$")
    private String balance;

    public AccountDto() {

    }

    public AccountDto(String name, String balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
