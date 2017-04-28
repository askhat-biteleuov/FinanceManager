package com.fm.internal.services;

import com.fm.internal.dtos.OutcomeDto;
import com.fm.internal.models.Account;
import com.fm.internal.models.Outcome;
import com.fm.internal.models.User;

import java.time.LocalDate;
import java.util.List;

public interface OutcomeService {
    void addOutcome(Outcome outcome);

    void deleteOutcome(Outcome outcome);

    void updateOutcome(Outcome outcome);

    Outcome findById(long id);

    List<Outcome> findAllOutcomesInAccount(Account account);

    List<Outcome> findOutcomesInAccountByDate(Account account, LocalDate start, LocalDate end);

    List<Outcome> getUserOutcomesPage(User user, int first, int limit);

    Long getUserOutcomesNumber(User user);

    List<Outcome> getPageOfOutcomes(Account account, int first, int limit);

    Long getAmountOfOutcomesInAccount(Account account);

    Outcome createOutcomeFromDto(OutcomeDto outcomeDto);

    List<Outcome> getUserOutcomes(User user);
}
