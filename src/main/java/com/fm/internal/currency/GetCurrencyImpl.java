package com.fm.internal.currency;

import com.fm.internal.models.Currency;
import com.fm.internal.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
public class GetCurrencyImpl implements GetCurrency {

    private final CurrencyService currencyService;
    private final CbrClient client;

    @Autowired
    public GetCurrencyImpl(CurrencyService currencyService, CbrClient client) {
        this.currencyService = currencyService;
        this.client = client;
    }

    @Scheduled(fixedDelay = 3600000)
    @Transactional
    @Override
    public void execute() {
        List<CurrencyData.ValuteCursOnDate> allCurrencyCursForNow = client.getAllCurrencyCursForNow();
        currencyService.addOrUpdateCurrency(new Currency("Российский рубль", BigDecimal.ONE, BigDecimal.ONE,
                643, "RUB"));
        allCurrencyCursForNow.forEach(currencyData -> {
            Currency currency = getCurrencyFromCurrencyData(currencyData);
            currencyService.addOrUpdateCurrency(currency);
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
