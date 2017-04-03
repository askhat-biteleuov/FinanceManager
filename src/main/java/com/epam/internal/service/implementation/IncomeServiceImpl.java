package com.epam.internal.service.implementation;

import com.epam.internal.dao.IncomeDao;
import com.epam.internal.data.entities.Account;
import com.epam.internal.data.entities.Income;
import com.epam.internal.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeDao dao;

    @Override
    public void addIncome(Income income) {
        dao.addIncome(income);
    }

    @Override
    public void updateIncome(Income income) {
        dao.updateIncome(income);
    }

    @Override
    public void deleteIncome(Income income) {
        dao.deleteIncome(income);
    }

    @Override
    public List<Income> findAllIncomesInAccount(Account account) {
        return dao.getAllIncomes(account);
    }
}
