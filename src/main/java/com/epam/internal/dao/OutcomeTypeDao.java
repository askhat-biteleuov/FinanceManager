package com.epam.internal.dao;

import com.epam.internal.data.entities.OutcomeType;
import com.epam.internal.data.entities.User;

import java.util.List;

public interface OutcomeTypeDao {
    void addType(OutcomeType type);
    void deleteType(OutcomeType type);
    void updateType(OutcomeType type);
    List<OutcomeType> getAvailableTypes(User user);
}
