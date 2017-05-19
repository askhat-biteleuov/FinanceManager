package com.fm.internal.dtos;

import org.hibernate.validator.constraints.Length;

public class PasswordDto {
    @Length(min = 6, max = 24)
    private String oldPassword;
    @Length(min = 6, max = 24)
    private String password;
    @Length(min = 6, max = 24)
    private String confirm;

    public PasswordDto() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
