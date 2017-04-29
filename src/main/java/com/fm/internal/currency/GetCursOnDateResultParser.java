package com.fm.internal.currency;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

public class GetCursOnDateResultParser {
//    public static class Currency {
//        public String name;
//        public String chCode;
//        public int code;
//        public BigDecimal nom;
//        public BigDecimal curs;
//
//        public Currency() {
//
//        }
//
//        public Currency(String vname, String vchcode, int vcode, BigDecimal vnom, BigDecimal vcurs) {
//            this.name = vname;
//            this.chCode = vchcode;
//            this.code = vcode;
//            this.nom = vnom;
//            this.curs = vcurs;
//        }
//    }
//
//    public static Currency getValuteByValuteCh(String valuteCh, GetCursOnDateXMLResponse.GetCursOnDateXMLResult result) throws Exception {
//
//        Currency answer = new Currency();
//
//        List<Object> list = result.getContent();
//        ElementNSImpl e = (ElementNSImpl) list.get(0);
//        NodeList chCodeList = e.getElementsByTagName("VchCode");
//        int length = chCodeList.getLength();
//
//        boolean isFound = false;
//        namesOfFields(valuteCh, answer, chCodeList, length, isFound);
//
//        return answer;
//
//    }
//
//    public static Currency getValuteByValuteCode(String valuteCode, GetCursOnDateXMLResponse.GetCursOnDateXMLResult result) throws Exception {
//
//        Currency answer = new Currency();
//
//        List<Object> list = result.getContent();
//        ElementNSImpl e = (ElementNSImpl) list.get(0);
//        NodeList chCodeList = e.getElementsByTagName("Vcode");
//        int length = chCodeList.getLength();
//
//        boolean isFound = false;
//        namesOfFields(valuteCode, answer, chCodeList, length, isFound);
//
//        return answer;
//
//    }
//
//    private static void namesOfFields(String valuteCode, Currency answer, NodeList chCodeList, int length, boolean isFound) {
//        for (int i = 0; i < length; i++) {
//            if (isFound) break;
//
//            Node valuteChNode = chCodeList.item(i);
//            TextImpl textimpl = (TextImpl) valuteChNode.getFirstChild();
//            String chVal = textimpl.getData();
//
//            if (chVal.equalsIgnoreCase(valuteCode)) {
//                isFound = true;
//                Node parent = valuteChNode.getParentNode();
//                NodeList nodeList = parent.getChildNodes();
//                int paramLength = nodeList.getLength();
//
//                for (int j = 0; j < paramLength; j++) {
//                    Node currentNode = nodeList.item(j);
//
//                    String name = currentNode.getNodeName();
//                    Node currentValue = currentNode.getFirstChild();
//                    String value = currentValue.getNodeValue();
//                    if (name.equalsIgnoreCase("Vname")) {
//                        answer.name = value;
//                    }
//                    if (name.equalsIgnoreCase("Vnom")) {
//                        answer.nom = new BigDecimal(value);
//                    }
//                    if (name.equalsIgnoreCase("Vcurs")) {
//                        answer.curs = new BigDecimal(value);
//                    }
//                    if (name.equalsIgnoreCase("Vcode")) {
//                        answer.code = Integer.parseInt(value);
//                    }
//                    if (name.equalsIgnoreCase("VchCode")) {
//                        answer.chCode = value;
//                    }
//                }
//            }
//        }
//    }


    public static XMLGregorianCalendar getXMLGregorianCalendarNow()
            throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        return datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
    }
}
