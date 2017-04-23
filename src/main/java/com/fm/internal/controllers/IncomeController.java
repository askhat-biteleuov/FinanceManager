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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class IncomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private PaginationServiceImpl paginationService;

    @RequestMapping(value = "/addincome", method = RequestMethod.GET)
    public ModelAndView newIncome(WebRequest request) {
        String accountId = request.getParameter("accountId");
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setAccountId(Long.parseLong(accountId));
        return new ModelAndView("newincome", "incomeDto", incomeDto);
    }

    @RequestMapping(value = "/addincome", method = RequestMethod.POST)
    public ModelAndView addIncome(@Valid @RequestBody IncomeDto incomeDto, BindingResult result) {
        User user = userService.getLoggedUser();
        if (!result.hasErrors() && user != null) {
            Account account = accountService.findAccountById(incomeDto.getAccountId());
            if (account != null) {
                incomeService.addIncome(incomeDto, account);
                account.setBalance(account.getBalance().add(new BigDecimal(incomeDto.getAmount())));
                accountService.updateAccount(account);
                return new ModelAndView("redirect:" + "/index");
            }
        }
        return new ModelAndView("newincome");
    }

    @RequestMapping(value = "/income/page", method = RequestMethod.GET)
    public ModelAndView listOfIncomes(@RequestParam("accountId") Long accountId,
                                      @RequestParam(value = "pageId", required = false) Integer pageId) {
        if (pageId == null) {
            pageId = 1;
        }
        Account accountById = accountService.findAccountById(accountId);
        Long sizeOfIncomesInAccount = incomeService.getAmountOfIncomesInAccount(accountById);
        int pageSize = 10;
        PaginationDto paginationDto = paginationService.createPagination(accountId, pageId, pageSize, sizeOfIncomesInAccount, "/income/page");
        List<Income> pageOfIncomes = incomeService.getPageOfIncomes(accountById, paginationDto.getFirstItem(), pageSize);
        AccountDto accountDto = new AccountDto(accountId, accountById.getName(), accountById.getBalance().toString());
        accountDto.setIncomes(pageOfIncomes);
        ModelAndView modelAndView = new ModelAndView("incomes-list", "accountDto", accountDto);
        modelAndView.addObject("paginationDto", paginationDto);
        modelAndView.addObject("incomes", pageOfIncomes);
        return modelAndView;
    }

    @RequestMapping(value = "/income/delete", method = RequestMethod.POST)
    public ModelAndView deleteIncomeById(@RequestParam("incomeId") Long incomeId,
                                         final HttpServletRequest request) {
        incomeService.deleteIncome(incomeService.findById(incomeId));
        String referer = request.getHeader("referer");
        return new ModelAndView("redirect:" + referer);
    }
}
