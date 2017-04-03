package com.epam.internal.dao.implementation;

import com.epam.internal.dao.AbstractDao;
import com.epam.internal.dao.OutcomeDao;
import com.epam.internal.data.entities.Account;
import com.epam.internal.data.entities.Outcome;

import java.util.List;

public class OutcomeDaoImpl extends AbstractDao<Integer, Outcome> implements OutcomeDao {
    @Override
    public void addOutcome(Outcome outcome) {
        create(outcome);
    }

    @Override
    public void deleteOutcome(Outcome outcome) {
        delete(outcome);
    }

    @Override
    public void updateOutcome(Outcome outcome) {
        update(outcome);
    }

    @Override
    public List<Outcome> getAllOutcomesInAccount(Account account) {
        return account.getOutcomeTransactions();
    }
}
