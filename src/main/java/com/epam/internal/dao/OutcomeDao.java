package com.epam.internal.dao;

import com.epam.internal.data.entities.Outcome;

public interface OutcomeDao {
    void addOutcome(Outcome outcome);
    void deleteOutcome(Outcome outcome);
    void updateOutcome(Outcome outcome);
}
