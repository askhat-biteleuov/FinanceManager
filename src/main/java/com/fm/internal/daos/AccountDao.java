package com.fm.internal.daos;

import com.fm.internal.models.Account;

public class AccountDao extends GenericDao<Account> {

    public AccountDao() {
        super(Account.class);
    }

}