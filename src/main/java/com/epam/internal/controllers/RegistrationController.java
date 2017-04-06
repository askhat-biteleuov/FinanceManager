package com.epam.internal.controllers;

import com.epam.internal.dtos.RegistrationDto;
import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;
import com.epam.internal.services.implementation.UserServiceImpl;
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
        return new ModelAndView("registration");
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView submit(@ModelAttribute("registrationDto") RegistrationDto registrationDto) {
        ModelAndView modelAndView = new ModelAndView();
        if (!Objects.equals(registrationDto.getPassword(), registrationDto.getConfirm())) {
            modelAndView.addObject("error", "Passwords don't match!");
            modelAndView.setViewName("registration");
            return modelAndView;
        }

        User user = userService.findByEmail(registrationDto.getEmail());
        boolean userExist = user != null;
        if (!userExist) {
            UserInfo userInfo = new UserInfo(registrationDto.getFirstName(), registrationDto.getLastName());
            user = new User(registrationDto.getEmail(), registrationDto.getPassword(), userInfo);
            userService.createUser(user);
            modelAndView.setViewName("login");
        } else {
            modelAndView.addObject("error", "User have already existed!");
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }
}
