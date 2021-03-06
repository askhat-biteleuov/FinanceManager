package com.fm.internal.services;

import com.fm.internal.dtos.OutcomeDto;
import com.fm.internal.models.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OutcomeService {
    void addOutcome(Outcome outcome);

    void deleteOutcome(Outcome outcome);

    void updateOutcome(Outcome outcome);

    Outcome findById(long id);

    List<Outcome> findAllOutcomesInAccount(Account account);

    List<Outcome> findOutcomesInAccountByDate(Account account, LocalDate start, LocalDate end);

    List<Outcome> getAccountOutcomesPageByDate(Account account, int first, int limit, LocalDate start, LocalDate end);

    List<Outcome> getUserOutcomesPageByDate(User user, int first, int limit, LocalDate start, LocalDate end);

    Long getUserOutcomesNumberByDate(User user, LocalDate start, LocalDate end);

    List<Outcome> getPageOfOutcomes(Account account, int first, int limit);

    Long getAccountOutcomesNumberByDate(Account account, LocalDate start, LocalDate end);

    Outcome createOutcomeFromDto(OutcomeDto outcomeDto);

    List<Outcome> getUserOutcomes(User user);

    BigDecimal getSumOfAllOutcomesForMonthForUser(User user);

    BigDecimal sumOfAllOutcomes(List<Outcome> outcomes);

    List<Outcome> getOutcomesByAccountAndHashTag(Account account, HashTag hashTag, LocalDate start, LocalDate end);

    List<Outcome> getAccountOutcomesPageByHashTagAndDate(Account account, HashTag hashTag, int offset, int limit, LocalDate start, LocalDate end);

    List<Outcome> getOutcomesByUserAndHashTag(User user, HashTag hashTag, LocalDate start, LocalDate end);

    List<Outcome> getUserOutcomesPageByHashTagAndDate(User user, HashTag hashTag, int offset, int limit, LocalDate start, LocalDate end);

    List<Outcome> getOutcomesByTypeAndHashTag(OutcomeType outcomeType, HashTag hashTag, LocalDate start, LocalDate end);

    List<Outcome> getTypeOutcomesPageByHashTagAndDate(OutcomeType outcomeType, HashTag hashTag, int offset, int limit, LocalDate start, LocalDate end);
}
