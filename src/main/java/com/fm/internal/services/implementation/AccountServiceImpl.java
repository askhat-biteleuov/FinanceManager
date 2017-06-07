package com.fm.internal.services.implementation;


import com.fm.internal.daos.AccountDao;
import com.fm.internal.dtos.AccountDto;
import com.fm.internal.dtos.TransferDto;
import com.fm.internal.models.*;
import com.fm.internal.services.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private HashTagService hashTagService;

    @Autowired
    private UtilServiceImpl utilService;

    @Override
    public List<Account> findAllUserAccounts(User user) {
        if (user == null) {
            return null;
        } else {
            return user.getAccounts().stream().filter(account -> account.getClass().equals(Account.class)).collect(Collectors.toList());
        }
    }

    @Override
    public List<Account> findAllUserAccountsAndGoals(User user) {
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
    public void makeTransferTo(TransferDto transferDto) {
        Account fromAccount = accountDao.getById(transferDto.getAccountId());
        Account toAccount = accountDao.getById(transferDto.getToAccountId());
        final String note = "Transfer from " + fromAccount.getClass().getSimpleName() + " " + fromAccount.getName() + " to " + toAccount.getClass().getSimpleName() + " " + toAccount.getName();
        User user = userService.getLoggedUser();
        List<HashTag> hashtags = new ArrayList<>();
        HashTag tag = hashTagService.getHashTagByUserAndText(user,"transfer");
        if(tag==null) {
            hashTagService.addHashTag(new HashTag("transfer",user));
            hashtags.add(hashTagService.getHashTagByUserAndText(user,"transfer"));
        }else{
            hashtags.add(tag);
        }
        Outcome transferOutcome = new Outcome(new BigDecimal(transferDto.getOutcomeAmount()), new BigDecimal(transferDto.getDefaultAmount()),
                LocalDate.parse(transferDto.getDate()), LocalTime.MIDNIGHT, note, hashtags, fromAccount,
                outcomeTypeService.getOutcomeTypeByNameAndUser(userService.getLoggedUser(), "Переводы"));
        outcomeService.addOutcome(transferOutcome);
        Income transferIncome = new Income(new BigDecimal(transferDto.getIncomeAmount()), LocalDate.parse(transferDto.getDate()), LocalTime.MIDNIGHT, note, hashtags, toAccount);
        transferIncome.setNote(note);
        incomeService.addIncome(transferIncome);
    }

    @Override
    public void makeTransferFrom(TransferDto transferDto) {
        Account fromAccount = accountDao.getById(transferDto.getToAccountId());
        Account toAccount = accountDao.getById(transferDto.getAccountId());
        final String note = "Transfer from " + fromAccount.getClass().getSimpleName() + " " + fromAccount.getName() + " to " + toAccount.getClass().getSimpleName() + " " + toAccount.getName();
        User user = userService.getLoggedUser();
        List<HashTag> hashtags = new ArrayList<>();
        HashTag tag = hashTagService.getHashTagByUserAndText(user,"transfer");
        if(tag==null) {
            hashTagService.addHashTag(new HashTag("transfer",user));
            hashtags.add(hashTagService.getHashTagByUserAndText(user,"transfer"));
        }else{
            hashtags.add(tag);
        }
        Outcome transferOutcome = new Outcome(new BigDecimal(transferDto.getIncomeAmount()), new BigDecimal(transferDto.getDefaultAmount()),
                LocalDate.parse(transferDto.getDate()), LocalTime.MIDNIGHT, note, hashtags, fromAccount,
                outcomeTypeService.getOutcomeTypeByNameAndUser(userService.getLoggedUser(), "Переводы"));
        outcomeService.addOutcome(transferOutcome);
        Income transferIncome = new Income(new BigDecimal(transferDto.getDefaultAmount()), LocalDate.parse(transferDto.getDate()), LocalTime.MIDNIGHT, note, hashtags, toAccount);
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
        Currency defaultCurrency = user.getInfo().getCurrency();
        return findAllUserAccounts(user).stream()
                .map(account -> account.getBalance()
                        .multiply(account.getCurrency().getCurs().divide(account.getCurrency().getNominal(), BigDecimal.ROUND_HALF_UP)))
                .map(amountInRoubles -> amountInRoubles
                        .multiply(defaultCurrency.getNominal().divide(defaultCurrency.getCurs(), RoundingMode.HALF_UP)))
                .reduce(BigDecimal::add).get();
    }

}