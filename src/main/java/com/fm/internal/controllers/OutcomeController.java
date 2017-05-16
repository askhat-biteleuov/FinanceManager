package com.fm.internal.controllers;

import com.fm.internal.dtos.OutcomeDto;
import com.fm.internal.dtos.PaginationDto;
import com.fm.internal.dtos.RangeDto;
import com.fm.internal.models.Account;
import com.fm.internal.models.Outcome;
import com.fm.internal.models.User;
import com.fm.internal.services.AccountService;
import com.fm.internal.services.OutcomeService;
import com.fm.internal.services.StatusBarService;
import com.fm.internal.services.UserService;
import com.fm.internal.services.implementation.PaginationServiceImpl;
import com.fm.internal.services.RangeService;
import com.fm.internal.validation.util.ValidErrors;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/outcome")
public class OutcomeController {
    private static final Logger LOGGER = Logger.getLogger(OutcomeController.class);

    @Autowired
    private OutcomeService outcomeService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PaginationServiceImpl paginationService;
    @Qualifier("messageSource")
    @Autowired
    private MessageSource messages;
    @Autowired
    private StatusBarService statusBarService;
    @Autowired
    private RangeService rangeService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ResponseBody
    public Object addOutcome(@RequestParam long accountId) {
        Account account = accountService.findAccountById(accountId);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object addOutcome(@Valid @RequestBody OutcomeDto outcomeDto,
                                  BindingResult result) {
        User user = userService.getLoggedUser();
        if (result.hasErrors() || user == null) {
            Map<String, String> errors = ValidErrors.getMapOfMessagesAndErrors(result, messages);
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        outcomeService.addOutcome(outcomeService.createOutcomeFromDto(outcomeDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView userOutcomes(@RequestParam(value = "pageId", required = false) Integer pageId,
                                     @ModelAttribute("rangeDto") RangeDto rangeDto) {
        if (pageId == null) {
            pageId = 1;
        }
        LocalDate start = rangeService.setupStart(rangeDto);
        LocalDate end = rangeService.setupEnd(rangeDto);
        User user = userService.getLoggedUser();
        Long userOutcomesNumber = outcomeService.getUserOutcomesNumberByDate(user, start, end);
        int pageSize = 10;
        PaginationDto paginationDto = paginationService.createPagination(user.getId(), pageId, pageSize,
                userOutcomesNumber, "/outcome/all");
        List<Outcome> outcomesPage = outcomeService.getUserOutcomesPageByDate(user, paginationDto.getFirstItem(),
                pageSize, start, end);
        ModelAndView modelAndView = new ModelAndView("user-outcomes");
        modelAndView.addObject("paginationDto", paginationDto);
        modelAndView.addObject("outcomes", outcomesPage);
        modelAndView.addObject("statusBarDto", statusBarService.getStatusBar(user));
        return modelAndView;
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ModelAndView listOfOutcomes(@RequestParam("itemId") Long accountId,
                                      @RequestParam(value = "pageId", required = false) Integer pageId,
                                      @ModelAttribute("rangeDto") RangeDto rangeDto) {
        if (pageId == null) {
            pageId = 1;
        }
        LocalDate start = rangeService.setupStart(rangeDto);
        LocalDate end = rangeService.setupEnd(rangeDto);
        Account accountById = accountService.findAccountById(accountId);
        Long amountOfOutcomesInAccount = outcomeService.getAccountOutcomesNumberByDate(accountById, start, end);
        int pageSize = 10;
        PaginationDto paginationDto = paginationService.createPagination(accountId, pageId, pageSize,
                amountOfOutcomesInAccount, "/outcome/page");
        List<Outcome> outcomesPage = outcomeService.getAccountOutcomesPageByDate(accountById,
                paginationDto.getFirstItem(), pageSize, start, end);
        rangeDto.setId(accountId);
        ModelAndView modelAndView = new ModelAndView("outcomes-list");
        modelAndView.addObject("outcomes", outcomesPage);
        modelAndView.addObject("paginationDto", paginationDto);
        modelAndView.addObject("statusBarDto", statusBarService.getStatusBar(userService.getLoggedUser()));
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView deleteOutcome(Long outcomeId, final HttpServletRequest request) {
        Outcome outcome = outcomeService.findById(outcomeId);
        outcomeService.deleteOutcome(outcome);
        String referer = request.getHeader("referer");
        return new ModelAndView("redirect:" + referer);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateOutcome(@RequestBody OutcomeDto outcomeDto) {
        Outcome outcome = outcomeService.findById(outcomeDto.getOutcomeId());
        if (outcome != null) {
            outcome.setNote(outcomeDto.getNote());
            outcomeService.updateOutcome(outcome);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
