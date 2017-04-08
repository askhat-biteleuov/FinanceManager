package com.epam.internal.controllers;

import com.epam.internal.dtos.AccountDto;
import com.epam.internal.models.User;
import com.epam.internal.services.AccountService;
import com.epam.internal.services.UserService;
import com.epam.internal.validation.AccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountValidator accountValidator;

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public ModelAndView getList() {
        return new ModelAndView("accounts", "accountDto", new AccountDto());
    }

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public ModelAndView submit(@Valid @ModelAttribute("accountDto") AccountDto accountDto, BindingResult result) {
        accountValidator.validate(accountDto, result);
        if (!result.hasErrors()) {
            User loggedUser = userService.findByEmail(getPrincipal());
            accountService.createAccount(accountDto, loggedUser);
            return new ModelAndView("redirect:" + "/index");
        }
        return new ModelAndView("accounts");
    }

    private String getPrincipal() {
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            return "anonymousUser";
        }
        return email;
    }
}
