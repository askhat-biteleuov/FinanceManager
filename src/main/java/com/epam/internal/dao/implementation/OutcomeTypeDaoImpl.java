package com.epam.internal.dao.implementation;

import com.epam.internal.dao.AbstractDao;
import com.epam.internal.dao.OutcomeTypeDao;
import com.epam.internal.data.entities.OutcomeType;
import com.epam.internal.data.entities.User;

import java.util.List;

public class OutcomeTypeDaoImpl extends AbstractDao<Integer, OutcomeType> implements OutcomeTypeDao {
    @Override
    public void addType(OutcomeType type) {
        create(type);
    }

    @Override
    public void deleteType(OutcomeType type) {
        delete(type);
    }

    @Override
    public void updateType(OutcomeType type) {
        update(type);
    }

    @Override
    public List<OutcomeType> getAvailableTypes(User user) {
        return user.getOutcomeTypes();
    }
}
