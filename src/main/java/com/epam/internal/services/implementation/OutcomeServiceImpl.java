package com.epam.internal.services.implementation;

import com.epam.internal.daos.GenericDao;
import com.epam.internal.models.Account;
import com.epam.internal.models.Outcome;
import com.epam.internal.services.OutcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class OutcomeServiceImpl implements OutcomeService {
    @Qualifier("outcomeDao")
    @Autowired
    private GenericDao<Outcome> dao;

    @Override
    public void addOutcome(Outcome outcome) {
        dao.create(outcome);
    }

    @Override
    public void deleteOutcome(Outcome outcome) {
        dao.delete(outcome);
    }

    @Override
    public void updateOutcome(Outcome outcome) {
        dao.update(outcome);
    }

    @Override
    public Outcome findById(long id) {
        return dao.findyById(id);
    }

    @Override
    public List<Outcome> getAllOutcomes(Account account) {
        return account.getOutcomeTransactions();
    }
}
