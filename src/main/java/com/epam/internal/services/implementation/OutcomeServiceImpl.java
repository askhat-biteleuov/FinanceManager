package com.epam.internal.services.implementation;

import com.epam.internal.daos.OutcomeDao;
import com.epam.internal.models.Account;
import com.epam.internal.models.Outcome;
import com.epam.internal.services.OutcomeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OutcomeServiceImpl implements OutcomeService {
    @Autowired
    private OutcomeDao dao;

    @Override
    public void addOutcome(Outcome outcome) {
        dao.addOutcome(outcome);
    }

    @Override
    public void deleteOutcome(Outcome outcome) {
        dao.deleteOutcome(outcome);
    }

    @Override
    public void updateOutcome(Outcome outcome) {
        dao.updateOutcome(outcome);
    }

    @Override
    public List<Outcome> getAllOutcomes(Account account) {
        return dao.getAllOutcomesInAccount(account);
    }
}
