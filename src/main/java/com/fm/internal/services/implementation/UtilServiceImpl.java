package com.fm.internal.services.implementation;

import com.fm.internal.models.Account;
import com.fm.internal.models.Income;
import com.fm.internal.models.Outcome;
import com.fm.internal.services.IncomeService;
import com.fm.internal.services.OutcomeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

public class UtilServiceImpl {
    @Autowired
    private OutcomeService outcomeService;
    @Autowired
    private IncomeService incomeService;

    public BigDecimal recountAccountBalance(Account account) {
        List<Outcome> allOutcomesInAccount = outcomeService.findAllOutcomesInAccount(account);
        List<Income> allIncomesInAccount = incomeService.findAllIncomesInAccount(account);
        BigDecimal sumOfIncomes = incomeService.sumOfAllIncomes(allIncomesInAccount);
        BigDecimal sumOfOutcomes = outcomeService.sumOfAllOutcomes(allOutcomesInAccount);
        return sumOfIncomes.subtract(sumOfOutcomes);
    }
}
