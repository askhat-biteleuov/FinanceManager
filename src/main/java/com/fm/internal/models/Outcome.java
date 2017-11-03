package com.fm.internal.models;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Outcome implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private BigDecimal defaultAmount;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;
    @Column
    private String note;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<HashTag> hashTags = new ArrayList<>();

    @ManyToOne
    @JoinColumn(nullable = false, name = "account_id", foreignKey = @ForeignKey(name = "fk_account_id"))
    private Account account;

    @ManyToOne
    @JoinColumn(nullable = false, name = "outcome_type_id", foreignKey = @ForeignKey(name = "fk_outcome_type_id"))
    private OutcomeType outcomeType;

    public Outcome() {
    }

    public Outcome(BigDecimal amount, BigDecimal defaultAmount, LocalDate date, LocalTime time, Account account, OutcomeType outcomeType) {
        this.amount = amount;
        this.defaultAmount = defaultAmount;
        this.date = date;
        this.time = time;
        this.account = account;
        this.outcomeType = outcomeType;
    }

    public Outcome(BigDecimal amount, BigDecimal defaultAmount, LocalDate date, LocalTime time, String note, List<HashTag> hashTags, Account account, OutcomeType outcomeType) {
        this.amount = amount;
        this.defaultAmount = defaultAmount;
        this.date = date;
        this.time = time;
        this.account = account;
        this.outcomeType = outcomeType;
        this.note = note;
        this.hashTags = hashTags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public OutcomeType getOutcomeType() {
        return outcomeType;
    }

    public void setOutcomeType(OutcomeType outcomeType) {
        this.outcomeType = outcomeType;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public BigDecimal getDefaultAmount() {
        return defaultAmount;
    }

    public void setDefaultAmount(BigDecimal defaultAmount) {
        this.defaultAmount = defaultAmount;
    }

    public List<HashTag> getHashTags() {
        return hashTags;
    }

    public void setHashTags(List<HashTag> hashTags) {
        this.hashTags = hashTags;
    }
}
