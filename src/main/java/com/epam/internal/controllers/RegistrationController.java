package com.epam.internal.controllers;

import com.epam.internal.dtos.RegistrationDto;
import com.epam.internal.models.User;
import com.epam.internal.services.UserService;
import com.epam.internal.validation.UserValidator;
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
        User user = userService.findByEmail(registrationDto.getEmail());
        if (user != null) {
            result.rejectValue("email", "Exist.registrationDto.email");
        }
        if (result.hasErrors()) {
            return new ModelAndView("registration");
        } else {
            userService.createUser(registrationDto);
            return new ModelAndView("login");
        }
    }
}
