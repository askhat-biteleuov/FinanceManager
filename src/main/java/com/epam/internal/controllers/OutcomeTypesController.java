package com.epam.internal.controllers;

import com.epam.internal.dtos.OutcomeTypeDto;
import com.epam.internal.models.User;
import com.epam.internal.services.OutcomeTypeService;
import com.epam.internal.services.UserService;
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
public class OutcomeTypesController {
    @Autowired
    private OutcomeTypeService typeService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/addouttype", method = RequestMethod.GET)
    public ModelAndView emptyList() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/addouttype", method = RequestMethod.POST)
    public ModelAndView addType() {
        return new ModelAndView("newoutcometype", "outcometypeDto", new OutcomeTypeDto());
    }

    @RequestMapping(value = "/newoutcometype", method = RequestMethod.POST)
    public ModelAndView newType(@Valid @ModelAttribute("outcometypeDto") OutcomeTypeDto outcomeTypeDto, BindingResult result) {
        if (!result.hasErrors()) {
            User user = userService.findByEmail(getPrincipal());
            typeService.addOutcomeType(outcomeTypeDto, user);
            return new ModelAndView("redirect:" + "/index");
        }
        return new ModelAndView("newoutcometype");
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
