package com.epam.internal.service.implementation;

import com.epam.internal.dao.OutcomeTypeDao;
import com.epam.internal.data.entities.OutcomeType;
import com.epam.internal.data.entities.User;
import com.epam.internal.service.OutcomeTypeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OutcomeTypeServiceImpl implements OutcomeTypeService {
    @Autowired
    private OutcomeTypeDao dao;

    @Override
    public List<OutcomeType> getAvailableOutcomeTypes(User user) {
        return dao.getAvailableTypes(user);
    }

    @Override
    public void addOutcomeType(OutcomeType type) {
        dao.addType(type);
    }

    @Override
    public void deleteOutcomeType(OutcomeType type) {
        dao.deleteType(type);
    }

    @Override
    public void updateOutcomeType(OutcomeType type) {
        dao.updateType(type);
    }
}
