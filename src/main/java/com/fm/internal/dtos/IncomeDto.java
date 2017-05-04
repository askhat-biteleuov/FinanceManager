package com.fm.internal.dtos;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

public class IncomeDto {
    @Pattern(regexp = "^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\\.[0-9]{2})?$")
    private String amount;
    @NotEmpty
    private String date;
    private String note;
    private long accountId;
    private String currency;

    public IncomeDto() {
    }

    public IncomeDto(String amount, String date, String note, long accountId, String currency) {
        this.amount = amount;
        this.date = date;
        this.note = note;
        this.accountId = accountId;
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
