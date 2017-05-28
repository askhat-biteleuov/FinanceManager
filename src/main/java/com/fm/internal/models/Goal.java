package com.fm.internal.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
public class Goal extends Account{
    @Column(nullable = false)
    private BigDecimal goalAmount;

    public Goal(){

    }

    public Goal(String name, BigDecimal balance, BigDecimal goalAmount, AccountType type, User user, Currency currency){
        super(name, balance, type, user, currency);
        this.goalAmount = goalAmount;
    }

    public BigDecimal getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(BigDecimal goalAmount) {
        this.goalAmount = goalAmount;
    }
}
