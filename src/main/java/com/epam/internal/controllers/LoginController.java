package com.epam.internal.controllers;

import com.epam.internal.models.User;
import com.epam.internal.services.AccountService;
import com.epam.internal.services.OutcomeTypeService;
import com.epam.internal.services.implementation.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OutcomeTypeService outcomeTypeService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView init() {
        LOGGER.info("Логинимся");
        return new ModelAndView("login");
    }

    @RequestMapping({"/index", "/"})
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        User loggedUser = userService.getLoggedUser();
        if (loggedUser == null) {
            modelAndView.addObject("notAuthenticated", true);
        } else {
            modelAndView.addObject("user", loggedUser);
            modelAndView.addObject("accounts", accountService.findAllUserAccounts(loggedUser));
            modelAndView.addObject("outcomeTypes", outcomeTypeService.getAvailableOutcomeTypes(loggedUser));
        }
        return modelAndView;
    }

}
