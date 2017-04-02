package com.epam.internal.dao.implementation;

import com.epam.internal.dao.AbstractDao;
import com.epam.internal.dao.AccountDao;
import com.epam.internal.data.entities.Account;
import com.epam.internal.data.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("accountDao")
public class AccountDaoImpl extends AbstractDao<Integer, Account> implements AccountDao {
    @Override
    public void updateInfo(Account account) {
        update(account);
    }

    @Override
    public void saveAccount(Account account) {
        create(account);
    }

    @Override
    public void deleteAccount(Account account) {
        delete(account);
    }

    @Override
    public List<Account> findAllUserAccounts(User user) {
        return user.getAccounts();
    }

}
