package com.fm.internal.dtos;


import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class TransferDto {

    @Pattern(regexp = "^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\\.[0-9]{2})?$")
    private String outcomeAmount;
    private String incomeAmount;
    private String defaultAmount;
    @NotEmpty
    private String date;
    private long accountId;
    @NotNull
    @Min(value = 1)
    private long toAccountId;

    public String getOutcomeAmount() {
        return outcomeAmount;
    }

    public void setOutcomeAmount(String outcomeAmount) {
        this.outcomeAmount = outcomeAmount;
    }

    public String getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(String incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public String getDefaultAmount() {
        return defaultAmount;
    }

    public void setDefaultAmount(String defaultAmount) {
        this.defaultAmount = defaultAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
