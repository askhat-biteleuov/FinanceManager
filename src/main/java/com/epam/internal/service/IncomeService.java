package com.epam.internal.service;

import com.epam.internal.data.entities.Account;
import com.epam.internal.data.entities.Income;

import java.util.List;

public interface IncomeService {
    void addIncome(Income income);
    void updateIncome(Income income);
    void deleteIncome(Income income);
    List<Income> findAllIncomesInAccount(Account account);
}
