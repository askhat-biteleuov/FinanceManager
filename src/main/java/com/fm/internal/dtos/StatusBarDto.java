package com.fm.internal.dtos;

import com.fm.internal.models.UserInfo;

import java.math.BigDecimal;

public class StatusBarDto {
    private BigDecimal sumOfAllBalancesOfAccounts;
    private BigDecimal sumOfAllOutcomesForMonthForUser;
    private UserInfo info;

    public StatusBarDto() {
    }

    public StatusBarDto(BigDecimal sumOfAllBalancesOfAccounts, BigDecimal sumOfAllOutcomesForMonthForUser, UserInfo info) {
        this.sumOfAllBalancesOfAccounts = sumOfAllBalancesOfAccounts;
        this.sumOfAllOutcomesForMonthForUser = sumOfAllOutcomesForMonthForUser;
        this.info = info;
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

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }
}
