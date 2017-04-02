package com.epam.internal.dao.implementation;

import com.epam.internal.dao.AbstractDao;
import com.epam.internal.dao.OutcomeDao;
import com.epam.internal.data.entities.Outcome;

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
}
