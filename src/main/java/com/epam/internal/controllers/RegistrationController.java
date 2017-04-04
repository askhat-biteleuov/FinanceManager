package com.epam.internal.controllers;

import com.epam.internal.DTO.RegistrationDTO;
import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;
import com.epam.internal.services.implementation.UserServiceImpl;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
public class RegistrationController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView init() {
        ModelAndView modelAndView = new ModelAndView("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView submit(@ModelAttribute("registrationDTO") RegistrationDTO registrationDTO) {
        ModelAndView modelAndView = new ModelAndView();
        if (!isPasswordsEquals(registrationDTO)) {
            modelAndView.addObject("error", "Passwords don't match!");
            modelAndView.setViewName("registration");
            return modelAndView;
        }
        UserInfo userInfo = new UserInfo(registrationDTO.getFirstName(), registrationDTO.getLastName());
        User user = new User(registrationDTO.getEmail(), registrationDTO.getPassword(), userInfo);
        try {
            userService.createUser(user);
            modelAndView.setViewName("login");
        } catch (ConstraintViolationException e) {
            modelAndView.addObject("error", "User have already existed!");
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }

    public boolean isPasswordsEquals(RegistrationDTO registrationDTO) {
        return Objects.equals(registrationDTO.getPassword(), registrationDTO.getConfirm());
    }
}
