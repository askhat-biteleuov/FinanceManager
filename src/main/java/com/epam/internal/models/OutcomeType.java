package com.epam.internal.models;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "name_user", columnNames = {"name", "user_id"})})
public class OutcomeType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "`limit`")
    private BigDecimal limit;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id", foreignKey = @ForeignKey(name = "fk_user_id"))
    private User user;

    public OutcomeType() {
    }

    public OutcomeType(String name, BigDecimal limit, User user) {
        this.name = name;
        this.limit = limit;
        this.user = user;
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

    public BigDecimal getLimit() {
        return limit;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
