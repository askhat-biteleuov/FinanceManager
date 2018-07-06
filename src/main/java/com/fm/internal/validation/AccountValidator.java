package com.fm.internal.validation;

import com.fm.internal.dtos.AccountDto;
import com.fm.internal.models.Account;
import com.fm.internal.services.AccountService;
import com.fm.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class AccountValidator implements Validator {

    private AccountService accountService;

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
            if (acc.getName().equalsIgnoreCase(accountDto.getName())) {
                errors.rejectValue("name", "Exist");
            }
        }
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
