package com.epam.internal.dao.implementation;

import com.epam.internal.dao.AbstractDao;
import com.epam.internal.dao.IncomeDao;
import com.epam.internal.data.entities.Income;

public class IncomeDaoImpl extends AbstractDao<Integer, Income> implements IncomeDao {
    @Override
    public void addIncome(Income income) {
        update(income);
    }

    @Override
    public void deleteIncome(Income income) {
        delete(income);
    }

    @Override
    public void updateIncome(Income income) {
        update(income);
    }
}
