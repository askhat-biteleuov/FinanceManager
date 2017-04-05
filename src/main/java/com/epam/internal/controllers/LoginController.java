package com.epam.internal.controllers;

import com.epam.internal.DTO.LoginDTO;
import com.epam.internal.models.User;
import com.epam.internal.services.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView init() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView submit(@ModelAttribute("loginDTO") LoginDTO loginDTO, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        if (loginDTO != null && loginDTO.getEmail() != null & loginDTO.getPassword() != null) {
            try {
                User user = userService.findByEmail(loginDTO.getEmail());
                if (loginDTO.getEmail().equals(user.getEmail()) && loginDTO.getPassword().equals(user.getPassword())) {
                    userService.saveUserToSession(user, session);
                    modelAndView.setViewName("index");
                } else {
                    modelAndView.addObject("error", "Password wrong!");
                    modelAndView.setViewName("login");
                }
            } catch (NoResultException e) {
                modelAndView.addObject("error", "User didn't find!");
                modelAndView.setViewName("login");
            }
            return modelAndView;
        } else {
            modelAndView.addObject("error", "Please enter details!");
            modelAndView.setViewName("login");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/exit", method = RequestMethod.GET)
    public ModelAndView exit(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("index");
        userService.removeUserFromSession(session);
        return modelAndView;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }
}
