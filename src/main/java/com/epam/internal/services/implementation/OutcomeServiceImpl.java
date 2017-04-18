package com.epam.internal.services.implementation;

import com.epam.internal.daos.OutcomeDao;
import com.epam.internal.dtos.OutcomeDto;
import com.epam.internal.models.Account;
import com.epam.internal.models.Outcome;
import com.epam.internal.models.OutcomeType;
import com.epam.internal.services.OutcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public void addOutcome(OutcomeDto outcomeDto, Account account, OutcomeType outcomeType) {
        Outcome income = new Outcome(new BigDecimal(outcomeDto.getAmount()), LocalDate.parse(outcomeDto.getDate()),
                LocalTime.now(), account, outcomeType);
        income.setNote(outcomeDto.getNote());
        dao.create(income);
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
    public List<Outcome> findOutcomesInAccountByDate(Account account, LocalDate date) {
        return dao.getIncomesInAccountByDate(account, date);
    }

    @Override
    public PagedListHolder<Outcome> getPagedOutcomeList(Account account, int pageSize) {
        List<Outcome> allOutcomesInAccount = dao.getAllAccountsOutcomes(account);
        PagedListHolder<Outcome> pagedList = new PagedListHolder<>(allOutcomesInAccount);
        pagedList.setPageSize(pageSize);
        return pagedList;
    }
}
