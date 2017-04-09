package com.epam.internal.validation;

import com.epam.internal.dtos.AccountDto;
import com.epam.internal.models.Account;
import com.epam.internal.services.AccountService;
import com.epam.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

public class AccountValidator implements Validator {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return AccountDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        AccountDto accountDto = (AccountDto) o;
        List<Account> accounts = accountService.findAllUserAccounts(userService.getLoggedUser());
        for (Account acc : accounts) {
            if (acc.getName().equals(accountDto.getName())) {
                errors.rejectValue("name", "Exist.accountDto.name");
            }
        }
    }
}
