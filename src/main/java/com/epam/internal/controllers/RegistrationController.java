package com.epam.internal.controllers;

import com.epam.internal.DTO.RegistrationDTO;
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
    public ModelAndView submit(@ModelAttribute("registrationDTO") RegistrationDTO registrationDTO) {
        ModelAndView modelAndView = new ModelAndView();
        if (!Objects.equals(registrationDTO.getPassword(), registrationDTO.getConfirm())) {
            modelAndView.addObject("error", "Passwords don't match!");
            modelAndView.setViewName("registration");
            return modelAndView;
        }
        User user = userService.findByEmail(registrationDTO.getEmail());
        boolean userExist = user != null;
        if (!userExist) {
            UserInfo userInfo = new UserInfo(registrationDTO.getFirstName(), registrationDTO.getLastName());
            user = new User(registrationDTO.getEmail(), registrationDTO.getPassword(), userInfo);
            userService.createUser(user);
            modelAndView.setViewName("login");
        } else {
            modelAndView.addObject("error", "User have already existed!");
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }
}
