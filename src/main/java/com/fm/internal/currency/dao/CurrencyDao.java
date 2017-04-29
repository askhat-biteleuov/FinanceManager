package com.fm.internal.currency.dao;

import com.fm.internal.currency.model.Currency;
import com.fm.internal.daos.GenericDao;

public class CurrencyDao extends GenericDao<Currency> {
    public CurrencyDao() {
        super(Currency.class);
    }
}
