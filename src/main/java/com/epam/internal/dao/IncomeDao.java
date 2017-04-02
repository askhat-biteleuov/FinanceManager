package com.epam.internal.dao;

import com.epam.internal.data.entities.Income;

public interface IncomeDao {
    void addIncome(Income income);
    void deleteIncome(Income income);
    void updateIncome(Income income);
}
