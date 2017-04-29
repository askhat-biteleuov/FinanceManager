package com.fm.internal.currency;

import ru.cbr.web.DailyInfo;
import ru.cbr.web.DailyInfoSoap;
import ru.cbr.web.GetCursOnDateResponse;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Optional;

public class TestCbrInfo {
    public static void main(String[] args) {
        DailyInfo service = new DailyInfo();
        DailyInfoSoap port = service.getDailyInfoSoap();

        XMLGregorianCalendar onDate = null;
        try {
            onDate = GetCursOnDateResultParser.getXMLGregorianCalendarNow();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        GetCursOnDateResponse.GetCursOnDateResult cursOnDate = port.getCursOnDate(onDate);
        Optional<CurrencyData.ValuteCursOnDate> eur = cursOnDate
                .getAny()
                .getRows()
                .stream()
                .filter(currency -> currency.getChCode().equals("EUR"))
                .findAny();
        System.out.println(eur.<Object>map(CurrencyData.ValuteCursOnDate::getCurs).orElse("нет такой валюты"));
    }
}
