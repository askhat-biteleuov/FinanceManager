package com.fm.internal.services.implementation;

import com.fm.internal.daos.OutcomeDao;
import com.fm.internal.daos.OutcomeTypeDao;
import com.fm.internal.dtos.OutcomeTypeDto;
import com.fm.internal.models.Account;
import com.fm.internal.models.Outcome;
import com.fm.internal.models.OutcomeType;
import com.fm.internal.models.User;
import com.fm.internal.repository.OutcomeRepository;
import com.fm.internal.repository.OutcomeTypeRepository;
import com.fm.internal.services.OutcomeTypeService;
import com.fm.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OutcomeTypeServiceImpl implements OutcomeTypeService {

    @Autowired
    private OutcomeRepository outcomeRepository;

    @Autowired
    private OutcomeTypeRepository outcomeTypeRepository;

    @Autowired
    private UserService userService;

    @Override
    public void addOutcomeType(OutcomeType type) {
        outcomeTypeRepository.save(type);
    }

    @Override
    public void addOutcomeType(OutcomeTypeDto typeDto, User user) {
        if (outcomeTypeRepository.findByUserAndName(user, typeDto.getName()) == null){
            OutcomeType outcomeType = new OutcomeType(typeDto.getName(), new BigDecimal(typeDto.getLimit()), user);
            outcomeTypeRepository.save(outcomeType);
        } else if (!outcomeTypeRepository.findByUserAndName(user, typeDto.getName()).isAvailable()){
            OutcomeType outcomeType = outcomeTypeRepository.findByUserAndName(user, typeDto.getName());
            outcomeType.setAvailable(true);
            outcomeType.setLimit(new BigDecimal(typeDto.getLimit()));
            outcomeTypeRepository.save(outcomeType);
        }
    }

    @Override
    public void deleteOutcomeType(OutcomeType type) {
        type.setAvailable(false);
        outcomeTypeRepository.save(type);
    }

    @Override
    public void deleteTypeAndUpdateOutcomes(OutcomeType oldOutcomeType, OutcomeType newOutcomeType) {
        outcomeRepository.updateOutcomeByType(oldOutcomeType, newOutcomeType);
        oldOutcomeType.setAvailable(false);
        outcomeTypeRepository.save(oldOutcomeType);
    }

    @Override
    public void updateOutcomeType(OutcomeType type) {
        outcomeTypeRepository.update(type);
    }

    @Override
    public OutcomeType findTypeById(long id) {
        return outcomeTypeRepository.getById(id);
    }

    @Override
    public List<OutcomeType> getAvailableOutcomeTypes(User user) {
        return outcomeTypeRepository.getAvailableOutcomeTypesByUser(user);
    }

    @Override
    public List<Outcome> getOutcomesOfTypeByDate(OutcomeType outcomeType, int first, int limit,
                                                 LocalDate start, LocalDate end) {
        return outcomeTypeRepository.getOutcomesByTypeByDate(outcomeType, first, limit, start, end);
    }

    @Override
    public Long getSizeOutcomesOfTypeByDate(OutcomeType outcomeType, LocalDate start, LocalDate end) {
        return outcomeTypeRepository.getOutcomesNumberByTypeByDate(outcomeType, start, end);
    }

    @Override
    public Map<String, Double> defaultOutcomeTypesValue(Account account) {
        LocalDate now = LocalDate.now();
        LocalDate start = LocalDate.of(now.getYear(), now.getMonth().getValue(), 1);
        LocalDate end = LocalDate.of(now.getYear(), now.getMonth().getValue(), 30);
        return outcomeTypeRepository.countOutcomeTypesValueByDate(account, start, end);
    }

    @Override
    public Map<String, Double> countOutcomeTypesValueByDate(Account account, LocalDate start, LocalDate end) {
        return outcomeTypeRepository.countOutcomeTypesValueByDate(account, start, end);
    }

    @Override
    public Map<Integer, Map<String, Double>> countOutcomeTypesValueByMonth(Account account, LocalDate start, List<OutcomeType> types) {
        Map<Integer, Map<String, Double>> outcomes = outcomeTypeRepository.countOutcomeTypesValueByMonth(account, start, types);
        for (OutcomeType type : types) {
            for (Integer day : outcomes.keySet()) {
                outcomes.get(day).putIfAbsent(type.getName(), 0.0);
            }
        }
        return outcomes;
    }

    @Override
    public Map<Integer, Map<String, Double>> countOutcomeTypesValueByYear(Account account, int year, List<OutcomeType> types) {
        Map<Integer, Map<String, Double>> outcomes = outcomeTypeRepository.countOutcomeTypesValueByYear(account, year, types);
        for (OutcomeType type : types) {
            for (Integer month : outcomes.keySet()) {
                outcomes.get(month).putIfAbsent(type.getName(), 0.0);
            }
        }
        return outcomes;
    }

    @Override
    public List<double[]> prepareChartData(Map<Integer, Map<String, Double>> data) {
        List<double[]> preaparedData = new ArrayList<>();
        int numOfTypes = data.get(0).size();
        for (Integer day : data.keySet()) {
            double[] dataArray = new double[numOfTypes];
            for (String type : data.get(day).keySet()) {
                dataArray[day] = data.get(day).get(type);
            }
            preaparedData.add(dataArray);
        }
        return preaparedData;
    }

    @Override
    public BigDecimal getSumOfOutcomesInTypeForMonth(OutcomeType outcomeType) {
        return outcomeTypeRepository.getSumOfOutcomesInTypeForMonth(outcomeType);
    }

    @Override
    public OutcomeType getOutcomeTypeByNameAndUser(User user, String name) {
        return outcomeTypeRepository.getOutcomeTypeByNameAndUser(user, name);
    }
}
