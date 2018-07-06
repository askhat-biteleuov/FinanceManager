package com.fm.internal.currency;

import com.fm.internal.daos.CurrencyDao;
import com.fm.internal.models.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class GetCurrencyImpl implements GetCurrency {

    private final CurrencyDao currencyDao;
    private final CbrClient client;

    @Autowired
    public GetCurrencyImpl(CurrencyDao currencyDao, CbrClient client) {
        this.currencyDao = currencyDao;
        this.client = client;
    }

    @Scheduled(fixedDelay = 3600000)
    @Override
    public void execute() {
        List<CurrencyData.ValuteCursOnDate> allCurrencyCursForNow = client.getAllCurrencyCursForNow();
        currencyDao.saveOrUpdate(new Currency("Российский рубль", BigDecimal.ONE, BigDecimal.ONE,
                643, "RUB"));
        allCurrencyCursForNow.forEach(currencyData -> {
            Currency currency = getCurrencyFromCurrencyData(currencyData);
            currencyDao.saveOrUpdate(currency);
        });
    }

    private Currency getCurrencyFromCurrencyData(CurrencyData.ValuteCursOnDate currencyData) {
        Currency currency = new Currency();
        currency.setCharacterCode(currencyData.getChCode());
        currency.setCode(currencyData.getCode());
        currency.setCurs(currencyData.getCurs());
        currency.setName(currencyData.getName());
        currency.setNominal(currencyData.getNom());
        return currency;
    }
}
