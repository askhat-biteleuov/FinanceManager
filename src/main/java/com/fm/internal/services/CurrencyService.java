package com.fm.internal.services;

import com.fm.internal.currency.model.Currency;

public interface CurrencyService {
    Currency findCurrencyByCharCode(String charCode);

    void updateCurrency(Currency currency);

    void addOrUpdateCurrency(Currency currency);

    void deleteCurrency(Currency currency);
}
