package com.epam.internal.service;

import com.epam.internal.data.entities.OutcomeType;
import com.epam.internal.data.entities.User;

import java.util.List;

public interface OutcomeTypeService {
    List<OutcomeType> getAvailableOutcomeTypes(User user);
    void addOutcomeType(OutcomeType type);
    void deleteOutcomeType(OutcomeType type);
    void updateOutcomeType(OutcomeType type);
}
