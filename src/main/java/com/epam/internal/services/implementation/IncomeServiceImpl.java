package com.epam.internal.services.implementation;

import com.epam.internal.daos.GenericDao;
import com.epam.internal.models.Account;
import com.epam.internal.models.Income;
import com.epam.internal.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class IncomeServiceImpl implements IncomeService {

    @Qualifier("incomeDao")
    @Autowired
    private GenericDao<Income> dao;

    @Override
    public void addIncome(Income income) {
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
        return dao.findyById(id);
    }

    @Override
    public List<Income> findAllIncomesInAccount(Account account) {
        return account.getIncomeTransactions();
    }
}
