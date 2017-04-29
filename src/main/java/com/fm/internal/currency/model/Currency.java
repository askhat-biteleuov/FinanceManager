package com.fm.internal.currency.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private BigDecimal nominal;

    private BigDecimal curs;

    private int code;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
