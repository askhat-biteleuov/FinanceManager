package com.epam.internal.dtos;

import javax.validation.constraints.Pattern;

public class IncomeDto {
    @Pattern(regexp = "^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\\.[0-9]{2})?$")
    private String amount;
    private String dateTime;
    private String note;
    private long accountId;

    public IncomeDto() {
    }

    public IncomeDto(String amount, String dateTime, String note, long accountId) {
        this.amount = amount;
        this.dateTime = dateTime;
        this.note = note;
        this.accountId = accountId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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
}
