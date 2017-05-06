package com.fm.internal.controllers;

import com.fm.internal.dtos.AccountDto;
import com.fm.internal.dtos.IncomeDto;
import com.fm.internal.dtos.PaginationDto;
import com.fm.internal.dtos.RangeDto;
import com.fm.internal.models.Account;
import com.fm.internal.models.Income;
import com.fm.internal.models.User;
import com.fm.internal.services.AccountService;
import com.fm.internal.services.IncomeService;
import com.fm.internal.services.StatusBarService;
import com.fm.internal.services.UserService;
import com.fm.internal.services.implementation.PaginationServiceImpl;
import com.fm.internal.services.implementation.RangeService;
import com.fm.internal.validation.util.ValidErrors;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/account/income")
public class IncomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private PaginationServiceImpl paginationService;
    @Qualifier("messageSource")
    @Autowired
    private MessageSource messages;
    @Autowired
    private StatusBarService statusBarService;
    @Autowired
    private RangeService rangeService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object addIncome(@Valid @RequestBody IncomeDto incomeDto, BindingResult result) {
        User user = userService.getLoggedUser();
        if (result.hasErrors() || user == null) {
            Map<String, String> errors = ValidErrors.getMapOfMessagesAndErrors(result, messages);
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        incomeService.addIncome(incomeService.createIncomeFromDto(incomeDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView userIncomes(@RequestParam(value = "pageId", required = false) Integer pageId,
                                    @ModelAttribute("rangeDto") RangeDto rangeDto) {
        if (pageId == null) {
            pageId = 1;
        }
        LocalDate start = rangeService.setupStart(rangeDto);
        LocalDate end = rangeService.setupEnd(rangeDto);
        User user = userService.getLoggedUser();
        Long userIncomesNumber = incomeService.getUserIncomesNumberByDate(user, start, end);
        int pageSize = 10;
        PaginationDto paginationDto = paginationService.createPagination(user.getId(), pageId, pageSize,
                userIncomesNumber, "/account/income/all");
        List<Income> incomesPage = incomeService.getUserIncomesPageByDate(user, paginationDto.getFirstItem(),
                pageSize, start, end);
        ModelAndView modelAndView = new ModelAndView("user-incomes");
        modelAndView.addObject("paginationDto", paginationDto);
        modelAndView.addObject("incomes", incomesPage);
        modelAndView.addObject("statusBarDto", statusBarService.getStatusBar(user));
        return modelAndView;
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ModelAndView listOfIncomes(@RequestParam("itemId") Long accountId,
                                      @RequestParam(value = "pageId", required = false) Integer pageId,
                                      @ModelAttribute("rangeDto") RangeDto rangeDto) {
        if (pageId == null) {
            pageId = 1;
        }
        LocalDate start = rangeService.setupStart(rangeDto);
        LocalDate end = rangeService.setupEnd(rangeDto);
        Account accountById = accountService.findAccountById(accountId);
        Long sizeOfIncomesInAccount = incomeService.getAccountIncomesNumberByDate(accountById, start, end);
        int pageSize = 10;
        PaginationDto paginationDto = paginationService.createPagination(accountId, pageId, pageSize,
                sizeOfIncomesInAccount, "/account/income/page");
        List<Income> incomesPage = incomeService.getAccountIncomesPageByDate(accountById, paginationDto.getFirstItem(),
                pageSize, start, end);
        rangeDto.setId(accountId);
        ModelAndView modelAndView = new ModelAndView("incomes-list");
        modelAndView.addObject("incomes", incomesPage);
        modelAndView.addObject("paginationDto", paginationDto);
        modelAndView.addObject("statusBarDto", statusBarService.getStatusBar(userService.getLoggedUser()));
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView deleteIncomeById(@RequestParam("incomeId") Long incomeId,
                                         final HttpServletRequest request) {
        incomeService.deleteIncome(incomeService.findById(incomeId));
        String referer = request.getHeader("referer");
        return new ModelAndView("redirect:" + referer);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateIncome(@RequestBody IncomeDto incomeDto) {
        Income incomeById = incomeService.findById(incomeDto.getAccountId());
        if (incomeById != null) {
            incomeById.setNote(incomeDto.getNote());
            incomeService.updateIncome(incomeById);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

