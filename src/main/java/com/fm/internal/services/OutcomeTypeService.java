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

    List<Outcome> getOutcomesOfTypeByDate(OutcomeType outcomeType, int first, int limit, LocalDate start, LocalDate end);

    Long getSizeOutcomesOfTypeByDate(OutcomeType outcomeType, LocalDate start, LocalDate end);

    Map<String, Double> defaultOutcomeTypesValue(Account account);

    Map<String, Double> countOutcomeTypesValueByDate(Account account, LocalDate start, LocalDate end);

    Map<Integer, Map<String, Double>> countOutcomeTypesValueByMonth(Account account, LocalDate start, List<OutcomeType> types);

    List<double[]> prepareChartData(Map<Integer, Map<String, Double>> data);

    BigDecimal getSumOfOutcomesInTypeForMonth(OutcomeType outcomeType);

    OutcomeType getOutcomeTypeByNameAndUser(User user, String name);
}
