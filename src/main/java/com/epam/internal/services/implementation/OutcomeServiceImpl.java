package com.epam.internal.services.implementation;

import com.epam.internal.daos.OutcomeDao;
import com.epam.internal.models.Account;
import com.epam.internal.models.Outcome;
import com.epam.internal.services.OutcomeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class OutcomeServiceImpl implements OutcomeService {
    @Autowired
    private OutcomeDao dao;

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
    public List<Outcome> findAllOutcomesInAccount(Account account) {
        return dao.getAllAccountsOutcomes(account);
    }

    @Override
    public List<Outcome> findOutcomesInAccountByDate(Account account, LocalDateTime date) {
        return dao.getIncomesInAccountByDate(account, date);
    }
}
