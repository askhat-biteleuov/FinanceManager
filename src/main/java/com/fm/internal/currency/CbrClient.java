package com.fm.internal.currency;

import ru.cbr.web.DailyInfo;
import ru.cbr.web.DailyInfoSoap;
import ru.cbr.web.GetCursOnDateResponse;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;
import java.util.List;

public class CbrClient {

    public CbrClient() {
    }

    List<CurrencyData.ValuteCursOnDate> getAllCurrencyCursForNow() {
        DailyInfo service = new DailyInfo();
        DailyInfoSoap port = service.getDailyInfoSoap();
        XMLGregorianCalendar date = null;
        try {
            date = getXMLGregorianCalendarNow();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        GetCursOnDateResponse.GetCursOnDateResult curs = port.getCursOnDate(date);
        return curs.getAny().getRows();
    }

    private XMLGregorianCalendar getXMLGregorianCalendarNow() throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        return datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
    }
}
