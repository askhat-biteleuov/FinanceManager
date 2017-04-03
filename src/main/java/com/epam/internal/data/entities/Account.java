package com.epam.internal.data.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "name_user",columnNames = {"name", "user_id"})})
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal balance;
    @OneToOne
    @JoinColumn(nullable = false, name = "account_type_id", foreignKey = @ForeignKey(name = "fk_account_type_id"))
    private AccountType type;
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id", foreignKey = @ForeignKey(name = "fk_user_id"))
    private User user;
    @OneToMany(mappedBy ="account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Income> incomeTransactions = new ArrayList<>();
    @OneToMany(mappedBy ="account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Outcome> outcomeTransactions = new ArrayList<>();

    public Account() {
    }

    public Account(String name, BigDecimal balance, AccountType type, User user) {
        this.name = name;
        this.balance = balance;
        this.type = type;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Income> getIncomeTransactions() {
        return incomeTransactions;
    }

    public void setIncomeTransactions(List<Income> incomeTransactions) {
        this.incomeTransactions = incomeTransactions;
    }

    public List<Outcome> getOutcomeTransactions() {
        return outcomeTransactions;
    }

    public void setOutcomeTransactions(List<Outcome> outcomeTransactions) {
        this.outcomeTransactions = outcomeTransactions;
    }
}
