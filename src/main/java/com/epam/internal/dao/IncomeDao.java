package com.epam.internal.dao;

import com.epam.internal.data.entities.Account;
import com.epam.internal.data.entities.Income;

import java.util.List;

public interface IncomeDao {
    void addIncome(Income income);
    void deleteIncome(Income income);
    void updateIncome(Income income);
    List<Income> getAllIncomes(Account account);
}
