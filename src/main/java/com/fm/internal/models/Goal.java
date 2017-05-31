package com.fm.internal.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table
public class Goal extends Account{
    @Column(nullable = false)
    private BigDecimal goalAmount;
    @Column(nullable = false)
    private LocalDate date;

    public Goal() {
    }

    public Goal(String name, BigDecimal balance, BigDecimal goalAmount, AccountType type, User user, Currency currency, LocalDate date) {
        super(name, balance, type, user, currency);
        this.goalAmount = goalAmount;
        this.date = date;
    }

    public BigDecimal getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(BigDecimal goalAmount) {
        this.goalAmount = goalAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
