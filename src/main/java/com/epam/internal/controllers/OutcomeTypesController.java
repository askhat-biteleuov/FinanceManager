package com.epam.internal.controllers;

import com.epam.internal.dtos.OutcomeTypeDto;
import com.epam.internal.models.Outcome;
import com.epam.internal.models.OutcomeType;
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
import java.util.List;

@Controller
public class OutcomeTypesController {
    @Autowired
    private OutcomeTypeService typeService;
    @Autowired
    private UserService userService;
    @Autowired
    private OutcomeTypeValidator validator;

    @RequestMapping(value = "/outcometype/page", method = RequestMethod.GET)
    public ModelAndView showOutcomeType(Long typeId, Integer pageId) {
        if (pageId == null) pageId = 1;
        int pageSize = 5;
        OutcomeType outcomeType = typeService.findTypeById(typeId);
        long size = typeService.getSizeOutcomesOfType(outcomeType);
        double numberOfPages = (double) size / pageSize;
        int count = (int) Math.ceil(numberOfPages);
        int numberOfItemsOnPage = pageId * pageSize > size ? (int) size : pageId * pageSize;
        List<Outcome> outcomes = typeService.getOutcomesOfType(outcomeType, (pageId - 1) * pageSize, numberOfItemsOnPage);

        OutcomeTypeDto outcomeTypeDto = new OutcomeTypeDto(typeId, outcomeType.getName(), outcomeType.getLimit().toString(), outcomes);
        ModelAndView modelAndView = new ModelAndView("outcometype", "outcomeTypeDto", outcomeTypeDto);
        modelAndView.addObject("count", count);
        return modelAndView;
    }

    @RequestMapping(value = "/outcometype/delete", method = RequestMethod.POST)
    public ModelAndView deleteOutcomeType(Long outcomeTypeId) {
        OutcomeType outcomeType = typeService.findTypeById(outcomeTypeId);
        typeService.deleteOutcomeType(outcomeType);
        return new ModelAndView("redirect:" + "/index");
    }

    @RequestMapping(value = "/outcometype/add", method = RequestMethod.GET)
    public ModelAndView addType() {
        return new ModelAndView("outcometype-add", "outcometypeDto", new OutcomeTypeDto());
    }

    @RequestMapping(value = "/outcometype/add", method = RequestMethod.POST)
    public ModelAndView newType(@Valid @ModelAttribute("outcometypeDto") OutcomeTypeDto outcomeTypeDto, BindingResult result) {
        validator.validate(outcomeTypeDto, result);
        User loggedUser = userService.getLoggedUser();
        if (!result.hasErrors() && loggedUser != null) {
            typeService.addOutcomeType(outcomeTypeDto, loggedUser);
            return new ModelAndView("redirect:" + "/index");
        }
        return new ModelAndView("outcometype-add");
    }

}
