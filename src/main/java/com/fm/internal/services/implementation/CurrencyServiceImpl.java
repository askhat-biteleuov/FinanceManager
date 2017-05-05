package com.fm.internal.services.implementation;

import com.fm.internal.daos.CurrencyDao;
import com.fm.internal.models.Currency;
import com.fm.internal.models.Account;
import com.fm.internal.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

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

    @Override
    public List<Currency> getCurrencies() {
        return currencyDao.getAll();
    }

    @Override
    public BigDecimal getOutcomeAmountForDefaultCurrency(Account account, BigDecimal amount) {
        if (account.getCurrency().getCode() != account.getUser().getInfo().getCurrency().getCode()) {
            BigDecimal defaultNominal = account.getUser().getInfo().getCurrency().getNominal();
            BigDecimal accountNominal = account.getCurrency().getNominal();
            BigDecimal defaultCurs = account.getUser().getInfo().getCurrency().getCurs().divide(defaultNominal, 4, 4);
            BigDecimal accountCurs = account.getCurrency().getCurs().divide(accountNominal, 4, 4);
            BigDecimal coefficient = accountCurs.divide(defaultCurs, 4, 4);
            return amount.multiply(coefficient);
        } else {
            return amount;
        }
    }
}
