package com.epam.internal.service.implementation;

import com.epam.internal.dao.OutcomeDao;
import com.epam.internal.data.entities.Account;
import com.epam.internal.data.entities.Outcome;
import com.epam.internal.service.OutcomeService;
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
