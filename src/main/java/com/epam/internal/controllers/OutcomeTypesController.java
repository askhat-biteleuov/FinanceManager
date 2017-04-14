package com.epam.internal.controllers;

import com.epam.internal.dtos.OutcomeTypeDto;
import com.epam.internal.models.User;
import com.epam.internal.services.OutcomeTypeService;
import com.epam.internal.services.UserService;
import com.epam.internal.validation.OutcomeTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/addouttype")
public class OutcomeTypesController {
    @Autowired
    private OutcomeTypeService typeService;
    @Autowired
    private UserService userService;
    @Autowired
    private OutcomeTypeValidator validator;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView addType() {
        return new ModelAndView("newoutcometype", "outcometypeDto", new OutcomeTypeDto());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView newType(@Valid @ModelAttribute("outcometypeDto") OutcomeTypeDto outcomeTypeDto, BindingResult result) {
        validator.validate(outcomeTypeDto, result);
        User loggedUser = userService.getLoggedUser();
        if (!result.hasErrors() && loggedUser != null) {
            typeService.addOutcomeType(outcomeTypeDto, loggedUser);
            return new ModelAndView("redirect:" + "/index");
        }
        return new ModelAndView("newoutcometype");
    }

}
