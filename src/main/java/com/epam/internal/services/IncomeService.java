package com.epam.internal.services;

import com.epam.internal.dtos.IncomeDto;
import com.epam.internal.models.Account;
import com.epam.internal.models.Income;
import org.springframework.beans.support.PagedListHolder;

import java.time.LocalDateTime;
import java.util.List;

public interface IncomeService {
    void addIncome(Income income);

    void addIncome(IncomeDto incomeDto, Account account);

    void updateIncome(Income income);

    void deleteIncome(Income income);

    Income findById(long id);

    List<Income> findAllIncomesInAccount(Account account);

    List<Income> findIncomesInAccountByDate(Account account, LocalDateTime date);

    PagedListHolder<Income> getPagedIncomeList(Account account, int pageSize);
}
