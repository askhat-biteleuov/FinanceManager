package com.epam.internal.services.implementation;

import com.epam.internal.daos.OutcomeTypeDao;
import com.epam.internal.dtos.OutcomeTypeDto;
import com.epam.internal.models.OutcomeType;
import com.epam.internal.models.User;
import com.epam.internal.services.OutcomeTypeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

public class OutcomeTypeServiceImpl implements OutcomeTypeService {

    @Autowired
    private OutcomeTypeDao dao;

    @Override
    public void addOutcomeType(OutcomeType type) {
        dao.create(type);
    }

    @Override
    public void addOutcomeType(OutcomeTypeDto typeDto, User user) {
        OutcomeType outcomeType = new OutcomeType(typeDto.getName(), new BigDecimal(typeDto.getLimit()), user);
        dao.create(outcomeType);
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
        return dao.findById(id);
    }

    @Override
    public List<OutcomeType> getAvailableOutcomeTypes(User user) {
        return dao.getAllUsersOutcomeTypes(user);
    }
}
