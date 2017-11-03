package com.fm.internal.services.implementation;

import com.fm.internal.daos.IncomeDao;
import com.fm.internal.dtos.IncomeDto;
import com.fm.internal.models.*;
import com.fm.internal.repository.IncomeRepository;
import com.fm.internal.services.AccountService;
import com.fm.internal.services.HashTagService;
import com.fm.internal.services.IncomeService;
import com.fm.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UtilServiceImpl utilService;
    @Autowired
    private HashTagService hashTagService;

    @Override
    public void addIncome(Income income) {
        incomeRepository.save(income);
        income.getAccount().setBalance(getBalanceAfterIncomeOperation(income));
        accountService.updateAccount(income.getAccount());
    }

    @Override
    public void updateIncome(Income income) {
        incomeRepository.save(income);
    }

    @Override
    public void deleteIncome(Income income) {
        incomeRepository.delete(income);
        income.getAccount().setBalance(getBalanceAfterIncomeOperation(income));
        accountService.updateAccount(income.getAccount());
    }

    private BigDecimal getBalanceAfterIncomeOperation(Income income) {
        Account account = income.getAccount();
        return utilService.recountAccountBalance(account);
    }

    @Override
    public Income findById(long id) {
        return incomeRepository.findOne(id);
    }

    @Override
    public List<Income> findAllIncomesInAccount(Account account) {
        return incomeRepository.findAllByAccount(account);
    }

    @Override
    public List<Income> findIncomesInAccountByDate(Account account, LocalDate start, LocalDate end) {
        return incomeRepository.findAllByAccountAndDateBetween(account, start, end);
    }

    @Override
    public List<Income> getAccountIncomesPageByDate(Account account, int first, int limit, LocalDate start, LocalDate end) {
        return incomeRepository.getAccountIncomesPageByDate(account, first, limit, start, end);
    }

    @Override
    public List<Income> getPageOfIncomes(Account account, int first, int limit) {
        return incomeRepository.getIncomesPage(account, first, limit);
    }

    @Override
    public List<Income> getUserIncomesPageByDate(User user, int first, int limit, LocalDate start, LocalDate end) {
        return incomeRepository.getUserIncomesPageByDate(user, first, limit, start, end);
    }

    @Override
    public Long getUserIncomesNumberByDate(User user, LocalDate start, LocalDate end) {
        return incomeRepository.getUserIncomesNumberByDate(user, start, end);
    }

    @Override
    public Long getAccountIncomesNumberByDate(Account account, LocalDate start, LocalDate end) {
        return incomeRepository.getAccountIncomeNumberByDate(account, start, end);
    }

    @Override
    public Income createIncomeFromDto(IncomeDto incomeDto) {
        User user = userService.getLoggedUser();
        Account account = accountService.findAccountById(incomeDto.getAccountId());
        Income income = new Income();
        income.setAccount(account);
        income.setNote(incomeDto.getNote());
        income.setAmount(new BigDecimal(incomeDto.getAmount()));
        income.setDate(LocalDate.parse(incomeDto.getDate()));
        income.setTime(LocalTime.now());
        income.setHashTags(utilService.parseHashTags(user, incomeDto.getHashTags()));
        return income;
    }

    @Override
    public BigDecimal sumOfAllIncomes(List<Income> incomes) {
        if (incomes.size() == 0) {
            return BigDecimal.ZERO;
        }
        return incomes.stream().map(Income::getAmount).reduce(BigDecimal::add).get();
    }

    @Override
    public List<Income> getIncomesByAccountAndHashTag(Account account, HashTag hashTag, LocalDate start, LocalDate end) {
        return incomeRepository.getIncomesByAccountAndHashTag(account, hashTag, start, end);
    }

    @Override
    public List<Income> getAccountIncomesPageByHashTagAndDate(Account account, HashTag hashTag, int offset, int limit, LocalDate start, LocalDate end) {
        return incomeRepository.getAccountIncomesPageByHashTagAndDate(account, hashTag, offset, limit, start, end);
    }

    @Override
    public List<Income> getIncomesByUserAndHashTag(User user, HashTag hashTag, LocalDate start, LocalDate end) {
        return incomeRepository.getIncomesByUserAndHashTag(user, hashTag, start, end);
    }

    @Override
    public List<Income> getUserIncomesPageByHashTagAndDate(User user, HashTag hashTag, int offset, int limit, LocalDate start, LocalDate end) {
        return incomeRepository.getUserIncomesPageByHashTagAndDate(user, hashTag, offset, limit, start, end);
    }
}
