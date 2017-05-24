package com.fm.internal.services.implementation;

import com.fm.internal.daos.OutcomeDao;
import com.fm.internal.dtos.OutcomeDto;
import com.fm.internal.models.Account;
import com.fm.internal.models.HashTag;
import com.fm.internal.models.Outcome;
import com.fm.internal.models.User;
import com.fm.internal.services.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class OutcomeServiceImpl implements OutcomeService {
    @Autowired
    private OutcomeDao dao;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OutcomeTypeService outcomeTypeService;
    @Autowired
    private UtilServiceImpl utilService;
    @Autowired
    private UserService userService;
    @Autowired
    private HashTagService hashTagService;

    @Override
    public void addOutcome(Outcome outcome) {
        dao.add(outcome);
        outcome.getAccount().setBalance(getBalanceAfterOutcomeOperation(outcome));
        accountService.updateAccount(outcome.getAccount());
    }

    @Override
    public void deleteOutcome(Outcome outcome) {
        dao.delete(outcome);
        outcome.getAccount().setBalance(getBalanceAfterOutcomeOperation(outcome));
        accountService.updateAccount(outcome.getAccount());
    }

    @Override
    public void updateOutcome(Outcome outcome) {
        dao.update(outcome);
    }

    private BigDecimal getBalanceAfterOutcomeOperation(Outcome outcome) {
        Account account = outcome.getAccount();
        return utilService.recountAccountBalance(account);
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
        start.lengthOfMonth();
        return dao.getAccountOutcomesByDate(account, start, end);
    }

    @Override
    public List<Outcome> getAccountOutcomesPageByDate(Account account, int first, int limit, LocalDate start, LocalDate end) {
        return dao.getAccountOutcomesPageByDate(account, first, limit, start, end);
    }

    @Override
    public List<Outcome> getUserOutcomesPageByDate(User user, int first, int limit, LocalDate start, LocalDate end) {
        return dao.getUserOutcomesPageByDate(user, first, limit, start, end);
    }

    @Override
    public Long getUserOutcomesNumberByDate(User user, LocalDate start, LocalDate end) {
        return dao.getUserOutcomesNumberByDate(user, start, end);
    }

    @Override
    public List<Outcome> getPageOfOutcomes(Account account, int first, int limit) {
        return dao.getOutcomesPage(account, first, limit);
    }

    @Override
    public Long getAccountOutcomesNumberByDate(Account account, LocalDate start, LocalDate end) {
        return dao.getAccountOutcomeNumberByDate(account, start, end);
    }

    @Override
    public Outcome createOutcomeFromDto(OutcomeDto outcomeDto) {
        User user = userService.getLoggedUser();

        Account account = accountService.findAccountById(outcomeDto.getAccountId());
        Outcome outcome = new Outcome();
        outcome.setAccount(account);
        outcome.setAmount(new BigDecimal(outcomeDto.getAmount()));
        outcome.setDate(LocalDate.parse(outcomeDto.getDate()));
        outcome.setTime(LocalTime.now());
        outcome.setNote(outcomeDto.getNote());
        outcome.setHashTags(utilService.parseHashTags(user, outcomeDto.getHashTags()));
        outcome.setOutcomeType(outcomeTypeService.findTypeById(outcomeDto.getOutcomeTypeId()));
        outcome.setDefaultAmount(new BigDecimal(outcomeDto.getDefaultAmount()));
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

    @Override
    public List<Outcome> getOutcomesByAccountAndHashTag(Account account, HashTag hashTag, LocalDate start, LocalDate end) {
        return dao.getOutcomesByAccountAndHashTag(account, hashTag, start, end);
    }


    @Override
    public List<Outcome> getAccountOutcomesPageByHashTagAndDate(Account account, HashTag hashTag, int offset, int limit, LocalDate start, LocalDate end) {
        return dao.getAccountOutcomesPageByHashTagAndDate(account, hashTag, offset, limit, start, end);
    }

    @Override
    public List<Outcome> getOutcomesByUserAndHashTag(User user, HashTag hashTag, LocalDate start, LocalDate end) {
        return dao.getOutcomesByUserAndHashTag(user, hashTag, start, end);
    }

    @Override
    public List<Outcome> getUserOutcomesPageByHashTagAndDate(User user, HashTag hashTag, int offset, int limit, LocalDate start, LocalDate end) {
        return dao.getUserOutcomesPageByHashTagAndDate(user, hashTag, offset, limit, start, end);
    }


}
