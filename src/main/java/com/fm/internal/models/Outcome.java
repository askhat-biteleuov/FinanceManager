package com.fm.internal.models;

import com.fm.internal.currency.model.Currency;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table
public class Outcome implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;
    @Column
    private String note;

    @ManyToOne
    @JoinColumn(nullable = false, name = "account_id", foreignKey = @ForeignKey(name = "fk_account_id"))
    private Account account;

    @ManyToOne
    @JoinColumn(nullable = false, name = "outcome_type_id", foreignKey = @ForeignKey(name = "fk_outcome_type_id"))
    private OutcomeType outcomeType;

    @ManyToOne
    @JoinColumn(nullable = false, name = "currency_character_code", foreignKey = @ForeignKey(name = "fk_currency_character_code"))
    private Currency currency;

    public Outcome() {
    }

    public Outcome(BigDecimal amount, LocalDate date, LocalTime time, Account account, OutcomeType outcomeType, Currency currency) {
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.account = account;
        this.outcomeType = outcomeType;
        this.currency = currency;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public OutcomeType getOutcomeType() {
        return outcomeType;
    }

    public void setOutcomeType(OutcomeType outcomeType) {
        this.outcomeType = outcomeType;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
