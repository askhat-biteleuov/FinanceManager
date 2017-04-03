package com.epam.internal.services;

import com.epam.internal.models.Account;
import com.epam.internal.models.Income;

import java.util.List;

public interface IncomeService {
    void addIncome(Income income);
    void updateIncome(Income income);
    void deleteIncome(Income income);
    Income findById(long id);
    List<Income> findAllIncomesInAccount(Account account);
}
