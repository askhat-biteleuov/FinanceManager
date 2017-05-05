package com.fm.internal.services;

import com.fm.internal.models.Currency;
import com.fm.internal.models.Account;

import java.math.BigDecimal;
import java.util.List;

public interface CurrencyService {
    Currency findCurrencyByCharCode(String charCode);

    void updateCurrency(Currency currency);

    void addOrUpdateCurrency(Currency currency);

    void deleteCurrency(Currency currency);

    List<Currency> getCurrencies();

    BigDecimal getOutcomeAmountForDefaultCurrency(Account account, BigDecimal amount);
}
