package com.fm.internal.services.implementation;


import com.fm.internal.daos.AccountDao;
import com.fm.internal.dtos.AccountDto;
import com.fm.internal.dtos.TransferDto;
import com.fm.internal.services.*;
import com.fm.internal.models.Account;
import com.fm.internal.models.Income;
import com.fm.internal.models.Outcome;
import com.fm.internal.models.User;
import com.fm.internal.services.AccountService;
import com.fm.internal.services.CurrencyService;
import com.fm.internal.services.IncomeService;
import com.fm.internal.services.OutcomeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserService userService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private OutcomeService outcomeService;

    @Autowired
    private OutcomeTypeService outcomeTypeService;

    @Autowired
    private UtilServiceImpl utilService;

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
    public void makeTransfer(TransferDto transferDto) {
        Account fromAccount = accountDao.getById(transferDto.getAccountId());
        Account toAccount = accountDao.getById(transferDto.getToAccountId());
        final String note = "Transfer from account "+fromAccount.getName()+" to account " + toAccount.getName();
        Outcome transferOutcome = new Outcome(new BigDecimal(transferDto.getOutcomeAmount()), new BigDecimal(transferDto.getDefaultAmount()),
                LocalDate.parse(transferDto.getDate()), LocalTime.MIDNIGHT, note, fromAccount,
                outcomeTypeService.getOutcomeTypeByNameAndUser(userService.getLoggedUser(), "Переводы"));
        outcomeService.addOutcome(transferOutcome);
        Income transferIncome = new Income(new BigDecimal(transferDto.getIncomeAmount()), LocalDate.parse(transferDto.getDate()), LocalTime.MIDNIGHT, toAccount);
        transferIncome.setNote(note);
        incomeService.addIncome(transferIncome);
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
        BigDecimal sumOfIncomes = BigDecimal.ZERO;
        Optional<BigDecimal> incBalance = findAllUserAccounts(user).stream()
                .map(account -> incomeService.findAllIncomesInAccount(account))
                .flatMap(Collection::stream)
                .map(Income::getAmount)
                .reduce(BigDecimal::add);
        if (incBalance.isPresent()) {
            sumOfIncomes = incBalance.get();
        }
        BigDecimal sumOfOutcomes = BigDecimal.ZERO;
        Optional<BigDecimal> outBalance = findAllUserAccounts(user).stream()
                .map(account -> outcomeService.findAllOutcomesInAccount(account))
                .flatMap(Collection::stream)
                .map(Outcome::getAmount)
                .reduce(BigDecimal::add);
        if (outBalance.isPresent()) {
            sumOfOutcomes = outBalance.get();
        }
        return sumOfIncomes.subtract(sumOfOutcomes);
    }
}