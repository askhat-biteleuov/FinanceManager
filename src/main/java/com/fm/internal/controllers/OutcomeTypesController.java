package com.fm.internal.controllers;

import com.fm.internal.dtos.OutcomeTypeDto;
import com.fm.internal.dtos.PaginationDto;
import com.fm.internal.models.Outcome;
import com.fm.internal.models.OutcomeType;
import com.fm.internal.models.User;
import com.fm.internal.services.OutcomeTypeService;
import com.fm.internal.services.UserService;
import com.fm.internal.services.implementation.PaginationServiceImpl;
import com.fm.internal.validation.OutcomeTypeValidator;
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
    @Autowired
    private PaginationServiceImpl paginationService;

    @RequestMapping(value = "/outcometype/page", method = RequestMethod.GET)
    public ModelAndView showOutcomeType(Long itemId, Integer pageId) {
        if (pageId == null) {
            pageId = 1;
        }
        OutcomeType outcomeType = typeService.findTypeById(itemId);
        long sizeOutcomesOfType = typeService.getSizeOutcomesOfType(outcomeType);
        int pageSize = 5;
        PaginationDto paginationDto = paginationService.createPagination(itemId, pageId, pageSize, sizeOutcomesOfType, "/outcometype/page");
        List<Outcome> outcomes = typeService.getOutcomesOfType(outcomeType, paginationDto.getFirstItem(), pageSize);

        OutcomeTypeDto outcomeTypeDto = new OutcomeTypeDto(itemId, outcomeType.getName(), outcomeType.getLimit().toString(), outcomes);
        ModelAndView modelAndView = new ModelAndView("outcometype", "outcomeTypeDto", outcomeTypeDto);
        modelAndView.addObject("paginationDto", paginationDto);
        return modelAndView;
    }


    @RequestMapping(value = "/outcometype/delete/all", method = RequestMethod.POST)
    public ModelAndView deleteOutcomeTypeWithOutcomes(Long outcomeTypeId) {
        OutcomeType outcomeType = typeService.findTypeById(outcomeTypeId);
        typeService.deleteOutcomeType(outcomeType);
        // TODO: 23.04.2017 fix cascade delete
        return new ModelAndView("redirect:" + "/index");
    }

    @RequestMapping(value = "/outcometype/delete/move", method = RequestMethod.POST)
    public ModelAndView deleteOutcomeTypeAndMoveOutcomes(Long outcomeTypeId) {
        // TODO: 23.04.2017 move all outcomes
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
