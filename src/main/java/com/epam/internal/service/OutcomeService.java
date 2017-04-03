package com.epam.internal.service;

import com.epam.internal.data.entities.Account;
import com.epam.internal.data.entities.Outcome;

import java.util.List;

public interface OutcomeService {
    void addOutcome(Outcome outcome);
    void deleteOutcome(Outcome outcome);
    void updateOutcome(Outcome outcome);
    List<Outcome> getAllOutcomes(Account account);
}
