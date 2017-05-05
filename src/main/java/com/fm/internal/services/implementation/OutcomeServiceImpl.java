package com.fm.internal.services.implementation;

import com.fm.internal.daos.OutcomeDao;
import com.fm.internal.dtos.OutcomeDto;
import com.fm.internal.models.Account;
import com.fm.internal.models.Outcome;
import com.fm.internal.models.User;
import com.fm.internal.services.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class OutcomeServiceImpl implements OutcomeService {
    @Autowired
    private OutcomeDao dao;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OutcomeTypeService outcomeTypeService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private UtilServiceImpl utilService;

    @Override
    public void addOutcome(Outcome outcome) {
        dao.add(outcome);
        outcome.getAccount().setBalance(getBalanceAfterAddingOutcome(outcome));
        accountService.updateAccount(outcome.getAccount());
    }

    private BigDecimal getBalanceAfterAddingOutcome(Outcome outcome) {
        Account account = outcome.getAccount();
        return utilService.recountAccountBalance(account);
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
        return dao.getById(id);
    }

    @Override
    public List<Outcome> findAllOutcomesInAccount(Account account) {
        return dao.getAccountOutcomes(account);
    }

    @Override
    public List<Outcome> findOutcomesInAccountByDate(Account account, LocalDate start, LocalDate end) {
        return dao.getAccountOutcomesByDate(account, start, end);
    }

    @Override
    public List<Outcome> getUserOutcomesPage(User user, int first, int limit) {
        return dao.getUserOutcomesPage(user, first, limit);
    }

    @Override
    public Long getUserOutcomesNumber(User user) {
        return dao.getUserOutcomesNumber(user);
    }

    @Override
    public List<Outcome> getPageOfOutcomes(Account account, int first, int limit) {
        return dao.getOutcomesPage(account, first, limit);
    }

    @Override
    public Long getAmountOfOutcomesInAccount(Account account) {
        return dao.getAccountOutcomeAmount(account);
    }

    @Override
    public Outcome createOutcomeFromDto(OutcomeDto outcomeDto) {
        Account account = accountService.findAccountById(outcomeDto.getAccountId());
        Outcome outcome = new Outcome();
        outcome.setAccount(account);
        outcome.setAmount(new BigDecimal(outcomeDto.getAmount()));
        outcome.setDate(LocalDate.parse(outcomeDto.getDate()));
        outcome.setTime(LocalTime.now());
        outcome.setNote(outcomeDto.getNote());
        outcome.setOutcomeType(outcomeTypeService.findTypeById(outcomeDto.getOutcomeTypeId()));
        outcome.setDefaultAmount(currencyService.getOutcomeAmountForDefaultCurrency(account, new BigDecimal(outcomeDto.getAmount())));
        return outcome;
    }

    @Override
    public List<Outcome> getUserOutcomes(User user) {
        return dao.getUserOutcomes(user);
    }

    @Override
    public BigDecimal getSumOfAllOutcomesForMonthForUser(User user) {
        return dao.getSumOfAllOutcomesForMonthForUser(user);
    }

    @Override
    public BigDecimal sumOfAllOutcomes(List<Outcome> outcomes) {
        if (outcomes.size() == 0) {
            return BigDecimal.ZERO;
        }
        return outcomes.stream().map(Outcome::getAmount).reduce(BigDecimal::add).get();
    }
}
