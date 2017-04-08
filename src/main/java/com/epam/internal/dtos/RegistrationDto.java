package com.epam.internal.dtos;


import org.hibernate.validator.constraints.Email;
import javax.validation.constraints.Min;

public class RegistrationDto {
    @Email(regexp = "^[_A-Za-z0-9-+]+" +
            "(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*" +
            "(.[A-Za-z]{2,})$")
    private String email;
    private String firstName;
    private String lastName;
    @Min(6)
    private String password;
    @Min(6)
    private String confirm;

    public RegistrationDto() {
    }

    public RegistrationDto(String email, String firstName, String lastName, String password, String confirm) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.confirm = confirm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
