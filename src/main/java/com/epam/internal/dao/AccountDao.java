package com.epam.internal.dao;

import com.epam.internal.data.entities.Account;
import com.epam.internal.data.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("accountDao")
public class AccountDao extends AbstractDao<Integer, Account> implements IAccountDao {
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
