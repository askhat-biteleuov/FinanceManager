package com.fm.internal.services.implementation;


import com.fm.internal.daos.AccountDao;
import com.fm.internal.dtos.AccountDto;
import com.fm.internal.models.Account;
import com.fm.internal.models.Income;
import com.fm.internal.models.User;
import com.fm.internal.services.AccountService;
import com.fm.internal.services.CurrencyService;
import com.fm.internal.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Currency;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private IncomeService incomeService;

    @Override
    public List<Account> findAllUserAccounts(User user) {
        if (user == null) {
            return null;
        } else {
            return user.getAccounts();
        }
    }

    @Override
    public Account findUserAccountByName(User user, String name) {
        if (user == null) {
            return null;
        } else {
            return user.getAccounts().stream().filter(account -> account.getName().equals(name)).findFirst().get();
        }
    }

    @Override
    public Account findAccountById(long id) {
        return accountDao.getById(id);
    }

    @Override
    public void makeTransfer(Account from, Account to, String amount) {
        from.setBalance(from.getBalance().subtract(new BigDecimal(amount)));
        accountDao.update(from);
        to.setBalance(to.getBalance().add(new BigDecimal(amount)));
        accountDao.update(to);
    }

    @Override
    public void createAccount(Account account) {
        accountDao.add(account);
    }

    @Override
    public void updateAccount(Account account) {
        accountDao.update(account);
    }

    @Override
    public void deleteAccount(Account account) {
        accountDao.delete(account);
    }

    @Override
    public void createAccount(AccountDto accountDto, User user) {
        Account account = new Account(accountDto.getName(), BigDecimal.ZERO, null, user,
                currencyService.findCurrencyByCharCode(accountDto.getCurrency()));
        accountDao.add(account);
        Income income = new Income(new BigDecimal(accountDto.getBalance()), LocalDate.now(), LocalTime.now(), account);
        income.setNote("Start balance");
        incomeService.addIncome(income);
    }

    @Override
    public BigDecimal getSumOfAllBalancesOfAccounts(User user) {
        return accountDao.getSumOfAllBalancesOfAccounts(user);
    }
}