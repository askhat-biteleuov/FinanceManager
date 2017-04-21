package com.fm.internal.services;

import com.fm.internal.dtos.OutcomeDto;
import com.fm.internal.models.Account;
import com.fm.internal.models.Outcome;
import com.fm.internal.models.OutcomeType;
import org.springframework.beans.support.PagedListHolder;

import java.time.LocalDate;
import java.util.List;

public interface OutcomeService {
    void addOutcome(Outcome outcome);

    void addOutcome(OutcomeDto outcomeDto, Account account, OutcomeType outcomeType);

    void deleteOutcome(Outcome outcome);

    void updateOutcome(Outcome outcome);

    Outcome findById(long id);

    List<Outcome> findAllOutcomesInAccount(Account account);

    List<Outcome> findOutcomesInAccountByDate(Account account, LocalDate start, LocalDate end);

    PagedListHolder<Outcome> getPagedOutcomeList(Account account, int pageSize);
}
