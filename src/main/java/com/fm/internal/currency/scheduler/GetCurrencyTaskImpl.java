package com.fm.internal.currency.scheduler;

import com.fm.internal.currency.CbrClient;
import com.fm.internal.currency.CurrencyData;
import com.fm.internal.currency.dao.CurrencyDao;
import com.fm.internal.currency.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.util.List;

public class GetCurrencyTaskImpl implements GetCurrencyTask {

    @Autowired
    private CurrencyDao currencyDao;
    @Autowired
    private CbrClient client;

    @Scheduled(fixedDelay = 5000)
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
