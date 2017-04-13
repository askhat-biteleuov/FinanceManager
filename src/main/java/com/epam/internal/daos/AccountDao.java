package com.epam.internal.daos;

import com.epam.internal.models.Account;

public class AccountDao extends GenericDao<Account> {

    public AccountDao() {
        super(Account.class);
    }

}