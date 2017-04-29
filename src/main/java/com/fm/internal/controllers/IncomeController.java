package com.fm.internal.controllers;

import com.fm.internal.dtos.AccountDto;
import com.fm.internal.dtos.IncomeDto;
import com.fm.internal.dtos.PaginationDto;
import com.fm.internal.models.Account;
import com.fm.internal.models.Income;
import com.fm.internal.models.User;
import com.fm.internal.services.AccountService;
import com.fm.internal.services.IncomeService;
import com.fm.internal.services.UserService;
import com.fm.internal.services.implementation.PaginationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object addIncome(@Valid @RequestBody IncomeDto incomeDto, BindingResult result) {
        User user = userService.getLoggedUser();
        if (!result.hasErrors() && user != null) {
            Account account = accountService.findAccountById(incomeDto.getAccountId());
            if (account != null) {
                incomeService.addIncome(incomeDto, account);
                account.setBalance(account.getBalance().add(new BigDecimal(incomeDto.getAmount())));
                accountService.updateAccount(account);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView userIncomes(@RequestParam(value = "pageId", required = false) Integer pageId) {
        if (pageId == null) {
            pageId = 1;
        }
        User user = userService.getLoggedUser();
        Long userIncomesNumber = incomeService.getUserIncomesNumber(user);
        int pageSize = 10;
        PaginationDto paginationDto = paginationService.createPagination(user.getId(), pageId, pageSize,
                userIncomesNumber, "/account/income/all");
        List<Income> incomesPage = incomeService.getUserIncomesPage(user, paginationDto.getFirstItem(), pageSize);
        ModelAndView modelAndView = new ModelAndView("user-incomes");
        modelAndView.addObject("paginationDto", paginationDto);
        modelAndView.addObject("incomes", incomesPage);
        return modelAndView;
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ModelAndView listOfIncomes(@RequestParam("itemId") Long accountId,
                                      @RequestParam(value = "pageId", required = false) Integer pageId) {
        if (pageId == null) {
            pageId = 1;
        }
        Account accountById = accountService.findAccountById(accountId);
        Long sizeOfIncomesInAccount = incomeService.getAmountOfIncomesInAccount(accountById);
        int pageSize = 10;
        PaginationDto paginationDto = paginationService.createPagination(accountId, pageId, pageSize, sizeOfIncomesInAccount, "/account/income/page");
        List<Income> pageOfIncomes = incomeService.getPageOfIncomes(accountById, paginationDto.getFirstItem(), pageSize);
        AccountDto accountDto = new AccountDto(accountId, accountById.getName(), accountById.getBalance().toString());
        accountDto.setIncomes(pageOfIncomes);
        ModelAndView modelAndView = new ModelAndView("incomes-list", "accountDto", accountDto);
        modelAndView.addObject("paginationDto", paginationDto);
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
