package com.fm.internal.services.implementation;

import com.fm.internal.daos.OutcomeDao;
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
    private OutcomeDao outcomeDao;

    @Autowired
    private OutcomeTypeDao outcomeTypeDao;

    @Override
    public void addOutcomeType(OutcomeType type) {
        outcomeTypeDao.add(type);
    }

    @Override
    public void addOutcomeType(OutcomeTypeDto typeDto, User user) {
        OutcomeType outcomeType = new OutcomeType(typeDto.getName(), new BigDecimal(typeDto.getLimit()), user);
        outcomeTypeDao.add(outcomeType);
    }

    @Override
    public void deleteOutcomeType(OutcomeType type) {
        outcomeDao.deleteOutcomeByType(type);
        outcomeTypeDao.delete(type);
    }

    @Override
    public void deleteTypeAndUpdateOutcomes(OutcomeType oldOutcomeType, OutcomeType newOutcomeType) {
        outcomeDao.updateOutcomeByType(oldOutcomeType, newOutcomeType);
        outcomeTypeDao.delete(oldOutcomeType);
    }

    @Override
    public void updateOutcomeType(OutcomeType type) {
        outcomeTypeDao.update(type);
    }

    @Override
    public OutcomeType findTypeById(long id) {
        return outcomeTypeDao.getById(id);
    }

    @Override
    public List<OutcomeType> getAvailableOutcomeTypes(User user) {
        return user != null ? user.getOutcomeTypes() : null;
    }

    @Override
    public List<Outcome> getOutcomesOfType(OutcomeType outcomeType, int first, int limit) {
        return outcomeTypeDao.getOutcomesByType(outcomeType, first, limit);
    }

    @Override
    public Long getSizeOutcomesOfType(OutcomeType outcomeType) {
        return outcomeTypeDao.getOutcomesNumberByType(outcomeType);
    }
}
