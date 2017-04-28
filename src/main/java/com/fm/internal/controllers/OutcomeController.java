package com.fm.internal.controllers;

import com.fm.internal.dtos.AccountDto;
import com.fm.internal.dtos.OutcomeDto;
import com.fm.internal.dtos.PaginationDto;
import com.fm.internal.models.Account;
import com.fm.internal.models.Outcome;
import com.fm.internal.models.OutcomeType;
import com.fm.internal.models.User;
import com.fm.internal.services.AccountService;
import com.fm.internal.services.OutcomeService;
import com.fm.internal.services.OutcomeTypeService;
import com.fm.internal.services.UserService;
import com.fm.internal.services.implementation.PaginationServiceImpl;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/outcome")
public class OutcomeController {
    private static final Logger LOGGER = Logger.getLogger(OutcomeController.class);

    @Autowired
    private OutcomeTypeService outcomeTypeService;
    @Autowired
    private OutcomeService outcomeService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PaginationServiceImpl paginationService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public void addOutcome(@Valid @RequestBody OutcomeDto outcomeDto,
                                  BindingResult result) {
        User user = userService.getLoggedUser();
        if (!result.hasErrors() && user != null) {
            saveNewOutcome(outcomeDto);
        }
    }

    private void saveNewOutcome(OutcomeDto outcomeDto) {
        Account account = accountService.findAccountById(outcomeDto.getAccountId());
        OutcomeType outcomeType = outcomeTypeService.findTypeById(outcomeDto.getOutcomeTypeId());
        Outcome newOutcome = outcomeService.createOutcomeFromDto(outcomeDto);
        newOutcome.setAccount(account);
        newOutcome.setOutcomeType(outcomeType);
        newOutcome.setNote(outcomeDto.getNote());
        saveNewOutcome(newOutcome, account);
    }

    private void saveNewOutcome(Outcome outcome, Account account) {
        outcomeService.addOutcome(outcome);
        account.setBalance(account.getBalance().subtract(outcome.getAmount()));
        accountService.updateAccount(account);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView userOutcomes(@RequestParam(value = "pageId", required = false) Integer pageId) {
        if (pageId == null) {
            pageId = 1;
        }
        User user = userService.getLoggedUser();
        Long userOutcomesNumber = outcomeService.getUserOutcomesNumber(user);
        int pageSize = 10;
        PaginationDto paginationDto = paginationService.createPagination(user.getId(), pageId, pageSize,
                userOutcomesNumber, "/outcome/all");
        List<Outcome> outcomesPage = outcomeService.getUserOutcomesPage(user, paginationDto.getFirstItem(), pageSize);
        ModelAndView modelAndView = new ModelAndView("user-outcomes");
        modelAndView.addObject("paginationDto", paginationDto);
        modelAndView.addObject("outcomes", outcomesPage);
        return modelAndView;
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ModelAndView listOfIncomes(@RequestParam("itemId") Long accountId,
                                      @RequestParam(value = "pageId", required = false) Integer pageId) {
        if (pageId == null) {
            pageId = 1;
        }
        Account accountById = accountService.findAccountById(accountId);
        Long amountOfOutcomesInAccount = outcomeService.getAmountOfOutcomesInAccount(accountById);
        int pageSize = 10;
        PaginationDto paginationDto = paginationService.createPagination(accountId, pageId, pageSize, amountOfOutcomesInAccount, "/outcome/page");
        List<Outcome> pageOfOutcomes = outcomeService.getPageOfOutcomes(accountById, paginationDto.getFirstItem(), pageSize);
        AccountDto accountDto = new AccountDto(accountId, accountById.getName(), accountById.getBalance().toString());
        accountDto.setOutcomes(pageOfOutcomes);
        ModelAndView modelAndView = new ModelAndView("outcomes-list", "accountDto", accountDto);
        modelAndView.addObject("paginationDto", paginationDto);
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView deleteOutcome(Long outcomeId, final HttpServletRequest request) {
        Outcome outcome = outcomeService.findById(outcomeId);
        outcomeService.deleteOutcome(outcome);
        String referer = request.getHeader("referer");
        return new ModelAndView("redirect:" + referer);
    }
}
