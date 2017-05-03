package com.fm.internal.services.implementation;

import com.fm.internal.currency.dao.CurrencyDao;
import com.fm.internal.currency.model.Currency;
import com.fm.internal.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;

public class CurrencyServiceImpl implements CurrencyService {
    @Autowired
    CurrencyDao currencyDao;

    @Override
    public Currency findCurrencyByCharCode(String charCode) {
        return currencyDao.getByCharCode(charCode);
    }

    @Override
    public void updateCurrency(Currency currency) {
        currencyDao.update(currency);
    }

    @Override
    public void addOrUpdateCurrency(Currency currency) {
        currencyDao.saveOrUpdate(currency);
    }

    @Override
    public void deleteCurrency(Currency currency) {
        currencyDao.delete(currency);
    }
}
