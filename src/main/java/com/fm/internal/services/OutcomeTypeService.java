package com.fm.internal.services;

import com.fm.internal.dtos.OutcomeTypeDto;
import com.fm.internal.models.Account;
import com.fm.internal.models.Outcome;
import com.fm.internal.models.OutcomeType;
import com.fm.internal.models.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OutcomeTypeService {
    void addOutcomeType(OutcomeType type);

    void addOutcomeType(OutcomeTypeDto typeDto, User user);

    void deleteOutcomeType(OutcomeType type);

    void deleteTypeAndUpdateOutcomes(OutcomeType oldOutcomeType, OutcomeType newOutcomeType);

    void updateOutcomeType(OutcomeType type);

    OutcomeType findTypeById(long id);

    List<OutcomeType> getAvailableOutcomeTypes(User user);

    List<Outcome> getOutcomesOfType(OutcomeType outcomeType, int first, int limit);

    Long getSizeOutcomesOfType(OutcomeType outcomeType);

    Map<String, Double> defaultOutcomeTypesValue(Account account);

    Map<String, Double> countOutcomeTypesValueByDate(Account account, LocalDate start, LocalDate end);

    BigDecimal getSumOfOutcomesInTypeForMonth(OutcomeType outcomeType);

    BigDecimal getSumOfAllLimitsForUser(User user);
}
