package com.epam.internal.validation;

import com.epam.internal.dtos.AccountDto;
import com.epam.internal.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AccountValidator implements Validator {

    @Autowired
    AccountService accountService;

    @Override
    public boolean supports(Class<?> aClass) {
        return AccountDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
