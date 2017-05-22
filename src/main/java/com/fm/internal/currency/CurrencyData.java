package com.fm.internal.currency;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrencyData {
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ValuteCursOnDate {
        @XmlElement(name = "Vname")
        private String name;
        @XmlElement(name = "Vnom")
        private BigDecimal nom;
        @XmlElement(name = "Vcurs")
        private BigDecimal curs;
        @XmlElement(name = "Vcode")
        private int code;
        @XmlElement(name = "VchCode")
        private String chCode;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getNom() {
            return nom;
        }

        public void setNom(BigDecimal nom) {
            this.nom = nom;
        }

        public BigDecimal getCurs() {
            return curs;
        }

        public void setCurs(BigDecimal curs) {
            this.curs = curs;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getChCode() {
            return chCode;
        }

        public void setChCode(String chCode) {
            this.chCode = chCode;
        }
    }

    @XmlElementWrapper(name = "ValuteData")
    @XmlElement(name = "ValuteCursOnDate")
    private List<ValuteCursOnDate> rows = new ArrayList<>();

    public List<ValuteCursOnDate> getRows() {
        return rows;
    }

    public void setRows(List<ValuteCursOnDate> rows) {
        this.rows = rows;
    }
}
