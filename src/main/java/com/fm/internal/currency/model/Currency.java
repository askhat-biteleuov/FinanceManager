package com.fm.internal.currency.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private BigDecimal nominal;

    private BigDecimal curs;

    private int code;

    @Id
    private String characterCode;

    public Currency() {
    }

    public Currency(String name, BigDecimal nominal, BigDecimal curs, int code, String characterCode) {
        this.name = name;
        this.nominal = nominal;
        this.curs = curs;
        this.code = code;
        this.characterCode = characterCode;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getNominal() {
        return nominal;
    }

    public void setNominal(BigDecimal nominal) {
        this.nominal = nominal;
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

    public String getCharacterCode() {
        return characterCode;
    }

    public void setCharacterCode(String characterCode) {
        this.characterCode = characterCode;
    }
}
