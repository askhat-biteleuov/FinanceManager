package com.epam.internal.controllers;

import com.epam.internal.dtos.IncomeDto;
import com.epam.internal.models.Account;
import com.epam.internal.models.Income;
import com.epam.internal.models.User;
import com.epam.internal.services.AccountService;
import com.epam.internal.services.IncomeService;
import com.epam.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping(value = "/addincome", method = RequestMethod.GET)
    public ModelAndView newIncome(WebRequest request) {
        String accountId = request.getParameter("accountId");
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setAccountId(Long.parseLong(accountId));
        return new ModelAndView("newincome", "incomeDto", incomeDto);
    }

    @RequestMapping(value = "/addincome", method = RequestMethod.POST)
    public ModelAndView addIncome(@Valid @ModelAttribute("incomeDto") IncomeDto incomeDto, BindingResult result) {
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

    @RequestMapping(value = "/income/list")
    public ModelAndView listOfIncomes(@RequestParam("accountId") int accountId, @RequestParam(required = false) Integer page) {
        ModelAndView modelAndView = new ModelAndView("incomes-list");
        Account accountById = accountService.findAccountById(accountId);
        List<Income> allIncomesInAccount = incomeService.findAllIncomesInAccount(accountById);
        PagedListHolder<Income> pagedListHolder = new PagedListHolder<>(allIncomesInAccount);
        final int pageSize = 5;
        final int maxPages = allIncomesInAccount.size() / pageSize;
        pagedListHolder.setPageSize(pageSize);
        modelAndView.addObject("maxPages", maxPages);
        if (page == null || page < 1 || page > pagedListHolder.getPageCount()) page = 1;
        modelAndView.addObject("page", page);
        if (page < 1 || page > pagedListHolder.getPageCount()) {
            pagedListHolder.setPage(0);
            modelAndView.addObject("incomes", pagedListHolder.getPageList());
        } else if (page <= pagedListHolder.getPageCount()) {
            pagedListHolder.setPage(page - 1);
            modelAndView.addObject("incomes", pagedListHolder.getPageList());
        }
        return modelAndView;
    }
}
