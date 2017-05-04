package com.fm.internal.dtos;

import java.math.BigDecimal;

public class StatusBarDto {
    private BigDecimal sumOfAllBalancesOfAccounts;
    private BigDecimal sumOfAllOutcomesForMonthForUser;

    public StatusBarDto() {
    }

    public StatusBarDto(BigDecimal sumOfAllBalancesOfAccounts, BigDecimal sumOfAllOutcomesForMonthForUser) {
        this.sumOfAllBalancesOfAccounts = sumOfAllBalancesOfAccounts;
        this.sumOfAllOutcomesForMonthForUser = sumOfAllOutcomesForMonthForUser;
    }

    public BigDecimal getSumOfAllBalancesOfAccounts() {
        return sumOfAllBalancesOfAccounts;
    }

    public void setSumOfAllBalancesOfAccounts(BigDecimal sumOfAllBalancesOfAccounts) {
        this.sumOfAllBalancesOfAccounts = sumOfAllBalancesOfAccounts;
    }

    public BigDecimal getSumOfAllOutcomesForMonthForUser() {
        return sumOfAllOutcomesForMonthForUser;
    }

    public void setSumOfAllOutcomesForMonthForUser(BigDecimal sumOfAllOutcomesForMonthForUser) {
        this.sumOfAllOutcomesForMonthForUser = sumOfAllOutcomesForMonthForUser;
    }
}
