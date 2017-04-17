package com.epam.internal.controllers;

import com.epam.internal.dtos.AccountDto;
import com.epam.internal.models.Account;
import com.epam.internal.models.User;
import com.epam.internal.services.AccountService;
import com.epam.internal.services.UserService;
import com.epam.internal.validation.AccountValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class AccountController {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountValidator accountValidator;

    @RequestMapping(value = "/add-account", method = RequestMethod.GET)
    public ModelAndView getList() {
        return new ModelAndView("account", "accountDto", new AccountDto());
    }

    @RequestMapping(value = "/add-account", method = RequestMethod.POST)
    public ModelAndView submit(@Valid @ModelAttribute("accountDto") AccountDto accountDto, BindingResult result) {
        accountValidator.validate(accountDto, result);
        User loggedUser = userService.getLoggedUser();
        if (!result.hasErrors() && loggedUser != null) {
            accountService.createAccount(accountDto, loggedUser);
            LOGGER.info("New account was create:" + accountDto.getName());
            return new ModelAndView("redirect:" + "/index");
        }
        return new ModelAndView("account");
    }

    @RequestMapping(value = "/account/page", method = RequestMethod.GET)
    public ModelAndView getAccountPage(@RequestParam("name") String nameOfAccount) {
        ModelAndView modelAndView = new ModelAndView("accountpage");
        Account accountByName = accountService.findUserAccountByName(userService.getLoggedUser(), nameOfAccount);
        modelAndView.addObject("account", accountByName);
        return modelAndView;
    }
}
