package com.epam.internal.controllers;

import com.epam.internal.dtos.RegistrationDto;
import com.epam.internal.services.UserService;
import com.epam.internal.validation.UserValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView init() {
        return new ModelAndView("registration", "registrationDto", new RegistrationDto());
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView submit(@Valid @ModelAttribute("registrationDto") RegistrationDto registrationDto, BindingResult result) {
        userValidator.validate(registrationDto, result);
        if (result.hasErrors()) {
            return new ModelAndView("registration");
        } else {
            userService.createUser(registrationDto);
            LOGGER.info("New user was registered:" + registrationDto.getEmail());
            return new ModelAndView("login");
        }
    }
}
