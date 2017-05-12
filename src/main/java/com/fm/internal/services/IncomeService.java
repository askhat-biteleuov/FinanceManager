package com.fm.internal.services;

import com.fm.internal.dtos.IncomeDto;
import com.fm.internal.models.Account;
import com.fm.internal.models.Income;
import com.fm.internal.models.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IncomeService {
    void addIncome(Income income);

    void updateIncome(Income income);

    void deleteIncome(Income income);

    Income findById(long id);

    List<Income> findAllIncomesInAccount(Account account);

    List<Income> findIncomesInAccountByDate(Account account, LocalDate start, LocalDate end);

    List<Income> getAccountIncomesPageByDate(Account account, int first, int limit, LocalDate start, LocalDate end);

    List<Income> getUserIncomesPageByDate(User user, int first, int limit, LocalDate start, LocalDate end);

    Long getUserIncomesNumberByDate(User user, LocalDate start, LocalDate end);

    List<Income> getPageOfIncomes(Account account, int first, int limit);

    Long getAccountIncomesNumberByDate(Account account, LocalDate start, LocalDate end);

    Income createIncomeFromDto(IncomeDto incomeDto);

    BigDecimal sumOfAllIncomes(List<Income> incomes);

    List<Income> getIncomesByHashTag(Account account, String hashTag);

}
