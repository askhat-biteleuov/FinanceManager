package com.fm.internal;

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
        System.out.println(eur.get().getCurs());
//        EnumValutesResponse.EnumValutesResult en = port.enumValutes(false);
//        GetCursOnDateResponse.GetCursOnDateResult curs = port.getCursOnDate(onDate);
//
//        onDate = port.getLatestDateTime();
//        GetCursOnDateXMLResponse.GetCursOnDateXMLResult result = port.getCursOnDateXML(onDate);
//        GetCursOnDateResultParser.Currency list = null;
//
//        try {
//            list = GetCursOnDateResultParser.getValuteByValuteCh("EUR", result);
//        } catch (Exception e) {
//
//        }
//        System.out.println(list.curs);
//
//        try {
//            list = GetCursOnDateResultParser.getValuteByValuteCode("840", result);
//        } catch (Exception e) {
//
//        }
//        System.out.println(list.curs);
    }
}
