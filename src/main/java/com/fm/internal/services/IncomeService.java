package com.fm.internal.services;

import com.fm.internal.dtos.IncomeDto;
import com.fm.internal.models.Account;
import com.fm.internal.models.Income;
import com.fm.internal.models.User;
import org.springframework.beans.support.PagedListHolder;

import java.time.LocalDate;
import java.util.List;

public interface IncomeService {
    void addIncome(Income income);

    void addIncome(IncomeDto incomeDto, Account account);

    void updateIncome(Income income);

    void deleteIncome(Income income);

    Income findById(long id);

    List<Income> findAllIncomesInAccount(Account account);

    List<Income> findIncomesInAccountByDate(Account account, LocalDate start, LocalDate end);

    List<Income> getUserIncomesPage(User user, int first, int limit);

    Long getUserIncomesNumber(User user);

    List<Income> getPageOfIncomes(Account account, int first, int limit);

    Long getAmountOfIncomesInAccount(Account account);

    PagedListHolder<Income> getPagedIncomeList(Account account, int pageSize);
}
