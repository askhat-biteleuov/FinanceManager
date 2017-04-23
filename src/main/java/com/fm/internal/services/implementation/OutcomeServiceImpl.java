package com.fm.internal.services.implementation;

import com.fm.internal.daos.OutcomeDao;
import com.fm.internal.dtos.OutcomeDto;
import com.fm.internal.models.Account;
import com.fm.internal.models.Outcome;
import com.fm.internal.services.OutcomeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class OutcomeServiceImpl implements OutcomeService {
    @Autowired
    private OutcomeDao dao;

    @Override
    public void addOutcome(Outcome outcome) {
        dao.create(outcome);
    }

    @Override
    public void deleteOutcome(Outcome outcome) {
        dao.delete(outcome);
    }

    @Override
    public void updateOutcome(Outcome outcome) {
        dao.update(outcome);
    }

    @Override
    public Outcome findById(long id) {
        return dao.findById(id);
    }

    @Override
    public List<Outcome> findAllOutcomesInAccount(Account account) {
        return dao.getAllAccountsOutcomes(account);
    }

    @Override
    public List<Outcome> findOutcomesInAccountByDate(Account account, LocalDate start, LocalDate end) {
        return dao.getOutcomeInAccountByDate(account, start, end);
    }

    @Override
    public List<Outcome> getPageOfOutcomes(Account account, int first, int limit) {
        return dao.getPageOfOutcomes(account, first, limit);
    }

    @Override
    public Long getAmountOfOutcomesInAccount(Account account) {
        return dao.getAmountOfOutcomesInAccount(account);
    }

    @Override
    public Outcome createOutcomeFromDto(OutcomeDto outcomeDto) {
        Outcome outcome = new Outcome();
        outcome.setAmount(new BigDecimal(outcomeDto.getAmount()));
        outcome.setDate(LocalDate.parse(outcomeDto.getDate()));
        outcome.setTime(LocalTime.now());
        return outcome;
    }
}
