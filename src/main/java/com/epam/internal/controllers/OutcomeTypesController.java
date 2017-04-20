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
        //init page number
        if (pageId == null) {
            pageId = 1;
        }
        //number of elements on page
        int pageSize = 5;
        OutcomeType outcomeType = typeService.findTypeById(typeId);
        //number of all elements into Type
        long size = typeService.getSizeOutcomesOfType(outcomeType);
        //count first and last element in sublist
        int firstItem = (pageId - 1) * pageSize;
        int lastItem = pageId * pageSize > size ? (int) size : pageId * pageSize;
        List<Outcome> outcomes = typeService.getOutcomesOfType(outcomeType, firstItem, lastItem);

        OutcomeTypeDto outcomeTypeDto = new OutcomeTypeDto(typeId, outcomeType.getName(), outcomeType.getLimit().toString(), outcomes);
        ModelAndView modelAndView = new ModelAndView("outcometype", "outcomeTypeDto", outcomeTypeDto);
        //number of pages
        double numberOfPages = (double) size / pageSize;
        int count = (int) Math.ceil(numberOfPages);
        modelAndView.addObject("count", count);
        //Count range of paginations links
        int startpage = pageId - 5 > 0 ? pageId - 5 : 1;
        int endpage = startpage + 10 < count ? startpage + 10 : count;

        modelAndView.addObject("startpage", startpage);
        modelAndView.addObject("endpage", endpage);
        modelAndView.addObject("selectedPage", pageId);

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
