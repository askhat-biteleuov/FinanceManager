package com.epam.internal.services;

import com.epam.internal.models.OutcomeType;
import com.epam.internal.models.User;

import java.util.List;

public interface OutcomeTypeService {
    void addOutcomeType(OutcomeType type);
    void deleteOutcomeType(OutcomeType type);
    void updateOutcomeType(OutcomeType type);
    OutcomeType findTypeById(long id);
    List<OutcomeType> getAvailableOutcomeTypes(User user);
}
