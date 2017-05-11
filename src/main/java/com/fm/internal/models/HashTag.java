package com.fm.internal.models;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class HashTag implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String text;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id", foreignKey = @ForeignKey(name = "fk_user_id"))
    private User user;

    public HashTag(){
    }

    public HashTag(String text, User user) {
        this.text = text;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
