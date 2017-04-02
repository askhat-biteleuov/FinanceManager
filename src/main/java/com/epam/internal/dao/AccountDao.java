package com.epam.internal.dao;

import com.epam.internal.data.entities.Account;
import com.epam.internal.data.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("accountDao")
public class AccountDao extends AbstractDao<Account> implements IGenericDao<Account> {
    public List<Account> findAllUserAccounts(User user) {
        return user.getAccounts();
    }
}
