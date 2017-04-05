package com.epam.internal.services;

import com.epam.internal.models.Account;
import com.epam.internal.models.Outcome;

import java.util.Date;
import java.util.List;

public interface OutcomeService {
    void addOutcome(Outcome outcome);

    void deleteOutcome(Outcome outcome);

    void updateOutcome(Outcome outcome);

    Outcome findById(long id);

    List<Outcome> findAllOutcomesInAccount(Account account);

    List<Outcome> findOutcomesInAccountByDate(Account account, Date date);
}
