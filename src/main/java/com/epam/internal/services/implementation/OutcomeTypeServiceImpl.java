package com.epam.internal.services.implementation;

import com.epam.internal.daos.GenericDao;
import com.epam.internal.models.OutcomeType;
import com.epam.internal.models.User;
import com.epam.internal.services.OutcomeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class OutcomeTypeServiceImpl implements OutcomeTypeService {
    @Qualifier("outcomeTypeDao")
    @Autowired
    private GenericDao<OutcomeType> dao;

    @Override
    public void addOutcomeType(OutcomeType type) {
        dao.create(type);
    }

    @Override
    public void deleteOutcomeType(OutcomeType type) {
        dao.delete(type);
    }

    @Override
    public void updateOutcomeType(OutcomeType type) {
        dao.update(type);
    }

    @Override
    public OutcomeType findTypeById(long id) {
        return dao.findyById(id);
    }

    @Override
    public List<OutcomeType> getAvailableOutcomeTypes(User user) {
        return user.getOutcomeTypes();
    }
}
