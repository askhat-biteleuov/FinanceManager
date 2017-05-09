package com.fm.internal.services;

import com.fm.internal.dtos.AccountDto;
import com.fm.internal.dtos.TransferDto;
import com.fm.internal.models.Account;
import com.fm.internal.models.User;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    List<Account> findAllUserAccounts(User user);

    Account findUserAccountByName(User user, String name);

    Account findAccountById(long id);

    void makeTransfer(TransferDto transferDto);

    void createAccount(Account account);

    void updateAccount(Account account);

    void deleteAccount(Account account);

    void createAccount(AccountDto accountDto, User user);

    BigDecimal getSumOfAllBalancesOfAccounts(User user);
}
