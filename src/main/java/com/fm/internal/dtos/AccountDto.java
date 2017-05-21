package com.fm.internal.dtos;

import com.fm.internal.models.Income;
import com.fm.internal.models.Outcome;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class AccountDto {
    private long id;
    @NotEmpty
    @Size(max = 15)
    private String name;

    @Pattern(regexp = "^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\\.[0-9]{2})?$")
    private String balance;

    private String currency;

    private List<Income> incomes;
    private List<Outcome> outcomes;

    public AccountDto() {

    }

    public AccountDto(String name, String balance, String currency) {
        this.name = name;
        this.balance = balance;
        this.currency = currency;
    }

    public AccountDto(long id, String name, String balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


    public List<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<Income> incomes) {
        this.incomes = incomes;
    }

    public List<Outcome> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<Outcome> outcomes) {
        this.outcomes = outcomes;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
