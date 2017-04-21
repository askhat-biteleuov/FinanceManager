package com.fm.internal.services.implementation;

import com.fm.internal.daos.OutcomeTypeDao;
import com.fm.internal.dtos.OutcomeTypeDto;
import com.fm.internal.models.Outcome;
import com.fm.internal.models.OutcomeType;
import com.fm.internal.models.User;
import com.fm.internal.services.OutcomeTypeService;
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
        return user != null ? user.getOutcomeTypes() : null;
    }

    @Override
    public List<Outcome> getOutcomesOfType(OutcomeType outcomeType, int first, int limit) {
        return dao.getOutcomesOfType(outcomeType, first, limit);
    }

    @Override
    public Long getSizeOutcomesOfType(OutcomeType outcomeType) {
        return dao.getSizeOutcomesOfType(outcomeType);
    }
}
