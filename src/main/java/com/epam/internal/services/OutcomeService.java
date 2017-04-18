package com.epam.internal.services;

import com.epam.internal.dtos.OutcomeDto;
import com.epam.internal.models.Account;
import com.epam.internal.models.Outcome;
import com.epam.internal.models.OutcomeType;
import org.springframework.beans.support.PagedListHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OutcomeService {
    void addOutcome(Outcome outcome);

    void addOutcome(OutcomeDto outcomeDto, Account account, OutcomeType outcomeType);

    void deleteOutcome(Outcome outcome);

    void updateOutcome(Outcome outcome);

    Outcome findById(long id);

    List<Outcome> findAllOutcomesInAccount(Account account);

    List<Outcome> findOutcomesInAccountByDate(Account account, LocalDate date);

    PagedListHolder<Outcome> getPagedOutcomeList(Account account, int pageSize);
}
