package com.epam.internal.dao;

import com.epam.internal.data.entities.Account;
import org.springframework.stereotype.Repository;

@Repository("accountDao")
public class AccountDao extends AbstractDao<Account> implements IGenericDao<Account> {

    @Override
    public Account findById(long id) {
        return findById(id);
    }

    @Override
    public void create(Account entity) {
        create(entity);
    }

    @Override
    public void delete(Account entity) {
        delete(entity);
    }

    @Override
    public void deleteById(long id) {
        deleteById(id);
    }
}
