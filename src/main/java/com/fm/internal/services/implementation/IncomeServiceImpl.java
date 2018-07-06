package com.fm.internal.services.implementation;

import com.fm.internal.daos.IncomeDao;
import com.fm.internal.dtos.IncomeDto;
import com.fm.internal.models.Account;
import com.fm.internal.models.HashTag;
import com.fm.internal.models.Income;
import com.fm.internal.models.User;
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

    private final IncomeDao dao;

    private UserService userService;
    private AccountService accountService;
    private UtilServiceImpl utilService;
    private HashTagService hashTagService;

    @Autowired
    public IncomeServiceImpl(IncomeDao dao) {
        this.dao = dao;
    }

    @Override
    public void addIncome(Income income) {
        dao.add(income);
        income.getAccount().setBalance(getBalanceAfterIncomeOperation(income));
        accountService.updateAccount(income.getAccount());
    }

    @Override
    public void updateIncome(Income income) {
        dao.update(income);
    }

    @Override
    public void deleteIncome(Income income) {
        dao.delete(income);
        income.getAccount().setBalance(getBalanceAfterIncomeOperation(income));
        accountService.updateAccount(income.getAccount());
    }

    private BigDecimal getBalanceAfterIncomeOperation(Income income) {
        Account account = income.getAccount();
        return utilService.recountAccountBalance(account);
    }

    @Override
    public Income findById(long id) {
        return dao.getById(id);
    }

    @Override
    public List<Income> findAllIncomesInAccount(Account account) {
        return dao.getAccountIncomes(account);
    }

    @Override
    public List<Income> findIncomesInAccountByDate(Account account, LocalDate start, LocalDate end) {
        return dao.getAccountIncomesByDate(account, start, end);
    }

    @Override
    public List<Income> getAccountIncomesPageByDate(Account account, int first, int limit, LocalDate start, LocalDate end) {
        return dao.getAccountIncomesPageByDate(account, first, limit, start, end);
    }

    @Override
    public List<Income> getPageOfIncomes(Account account, int first, int limit) {
        return dao.getIncomesPage(account, first, limit);
    }

    @Override
    public List<Income> getUserIncomesPageByDate(User user, int first, int limit, LocalDate start, LocalDate end) {
        return dao.getUserIncomesPageByDate(user, first, limit, start, end);
    }

    @Override
    public Long getUserIncomesNumberByDate(User user, LocalDate start, LocalDate end) {
        return dao.getUserIncomesNumberByDate(user, start, end);
    }

    @Override
    public Long getAccountIncomesNumberByDate(Account account, LocalDate start, LocalDate end) {
        return dao.getAccountIncomeNumberByDate(account, start, end);
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
        return dao.getIncomesByAccountAndHashTag(account, hashTag, start, end);
    }

    @Override
    public List<Income> getAccountIncomesPageByHashTagAndDate(Account account, HashTag hashTag, int offset, int limit, LocalDate start, LocalDate end) {
        return dao.getAccountIncomesPageByHashTagAndDate(account, hashTag, offset, limit, start, end);
    }

    @Override
    public List<Income> getIncomesByUserAndHashTag(User user, HashTag hashTag, LocalDate start, LocalDate end) {
        return dao.getIncomesByUserAndHashTag(user, hashTag, start, end);
    }

    @Override
    public List<Income> getUserIncomesPageByHashTagAndDate(User user, HashTag hashTag, int offset, int limit, LocalDate start, LocalDate end) {
        return dao.getUserIncomesPageByHashTagAndDate(user, hashTag, offset, limit, start, end);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setUtilService(UtilServiceImpl utilService) {
        this.utilService = utilService;
    }

    @Autowired
    public void setHashTagService(HashTagService hashTagService) {
        this.hashTagService = hashTagService;
    }
}
