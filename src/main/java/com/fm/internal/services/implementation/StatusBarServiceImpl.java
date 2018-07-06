package com.fm.internal.services.implementation;

import com.fm.internal.dtos.StatusBarDto;
import com.fm.internal.models.User;
import com.fm.internal.services.AccountService;
import com.fm.internal.services.OutcomeService;
import com.fm.internal.services.StatusBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatusBarServiceImpl implements StatusBarService {

    private AccountService accountService;
    private OutcomeService outcomeService;

    @Override
    public StatusBarDto getStatusBar(User user) {
        return new StatusBarDto(accountService.getSumOfAllBalancesOfAccounts(user),
                outcomeService.getSumOfAllOutcomesForMonthForUser(user), user.getInfo());
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setOutcomeService(OutcomeService outcomeService) {
        this.outcomeService = outcomeService;
    }
}
