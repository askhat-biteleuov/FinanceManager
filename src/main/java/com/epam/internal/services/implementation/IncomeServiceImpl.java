package com.epam.internal.services.implementation;

import com.epam.internal.daos.IncomeDao;
import com.epam.internal.dtos.IncomeDto;
import com.epam.internal.models.Account;
import com.epam.internal.models.Income;
import com.epam.internal.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeDao dao;

    @Override
    public void addIncome(Income income) {
        dao.create(income);
    }

    @Override
    public void addIncome(IncomeDto incomeDto, Account account) {
        LocalDate das = LocalDate.parse(incomeDto.getDate());
        Income income = new Income(new BigDecimal(incomeDto.getAmount()), LocalDate.parse(incomeDto.getDate()), LocalTime.now(), account);
        income.setNote(incomeDto.getNote());
        dao.create(income);
    }

    @Override
    public void updateIncome(Income income) {
        dao.update(income);
    }

    @Override
    public void deleteIncome(Income income) {
        dao.delete(income);
    }

    @Override
    public Income findById(long id) {
        return dao.findById(id);
    }

    @Override
    public List<Income> findAllIncomesInAccount(Account account) {
        return dao.getAccountsIncomes(account);
    }

    @Override
    public List<Income> findIncomesInAccountByDate(Account account, LocalDate date) {
        return dao.getIncomesInAccountByDate(account, date);
    }

    @Override
    public List<Income> findIncomesInAccountByMonth(Account account, LocalDate date) {
        return dao.getIncomesInAccountByMonth(account,date);
    }

    @Override
    public PagedListHolder<Income> getPagedIncomeList(Account account, int pageSize) {
        List<Income> allIncomesInAccount = dao.getAccountsIncomes(account);
        PagedListHolder<Income> pagedList = new PagedListHolder<>(allIncomesInAccount);
        pagedList.setPageSize(pageSize);
        return pagedList;
    }
}
