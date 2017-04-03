package com.epam.internal.services;

import com.epam.internal.models.Account;
import com.epam.internal.models.Outcome;

import java.util.List;

public interface OutcomeService {
    void addOutcome(Outcome outcome);
    void deleteOutcome(Outcome outcome);
    void updateOutcome(Outcome outcome);
    List<Outcome> getAllOutcomes(Account account);
}
