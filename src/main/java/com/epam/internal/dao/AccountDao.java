package com.epam.internal.dao;

import com.epam.internal.data.entities.Account;
import org.springframework.stereotype.Repository;

@Repository("accountDao")
public class AccountDao extends AbstractDao<Account> implements IGenericDao<Account> {

}
