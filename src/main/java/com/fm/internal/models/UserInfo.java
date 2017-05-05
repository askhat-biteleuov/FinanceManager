package com.fm.internal.models;

import com.fm.internal.currency.model.Currency;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @ManyToOne
    @JoinColumn(nullable = false, name = "currency_character_code", foreignKey = @ForeignKey(name = "fk_currency_character_code"))
    private Currency currency;

    public UserInfo() {
    }

    public UserInfo(String firstName, String lastName, Currency currency) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.currency = currency;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
