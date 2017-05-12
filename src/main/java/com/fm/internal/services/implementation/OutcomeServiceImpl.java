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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Autowired
    private UserService userService;
    @Autowired
    private HashTagService hashTagService;

    @Override
    public void addOutcome(Outcome outcome) {
        dao.add(outcome);
        outcome.getAccount().setBalance(getBalanceAfterOutcomeOperation(outcome));
        accountService.updateAccount(outcome.getAccount());
        List<HashTag> outComeHashTags = utilService.parseHashTags(outcome.getAccount().getUser(), outcome.getHashTags());
        outComeHashTags.stream().forEach(hashTag -> {
            if (hashTagService.getHashTagByUserAndText(outcome.getAccount().getUser(), hashTag.getText()) == null){
                hashTagService.addHashTag(hashTag);
            }
        });
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
        Account account = accountService.findAccountById(outcomeDto.getAccountId());
        Outcome outcome = new Outcome();
        outcome.setAccount(account);
        outcome.setAmount(new BigDecimal(outcomeDto.getAmount()));
        outcome.setDate(LocalDate.parse(outcomeDto.getDate()));
        outcome.setTime(LocalTime.now());
        outcome.setNote(outcomeDto.getNote());
        outcome.setHashTags(outcomeDto.getHashTags().toLowerCase());
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
    public List<Outcome> getOutcomesByHashTag(Account account, String hashTag) {
        return dao.getOutcomesByHashTag(account, hashTag);
    }
}
