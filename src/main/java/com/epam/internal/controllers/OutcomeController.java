package com.epam.internal.controllers;

import com.epam.internal.dtos.OutcomeDto;
import com.epam.internal.models.Account;
import com.epam.internal.models.OutcomeType;
import com.epam.internal.models.User;
import com.epam.internal.services.AccountService;
import com.epam.internal.services.OutcomeService;
import com.epam.internal.services.OutcomeTypeService;
import com.epam.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class OutcomeController {
    @Autowired
    private OutcomeTypeService outcomeTypeService;
    @Autowired
    private OutcomeService outcomeService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/addoutcome", method = RequestMethod.GET)
    public ModelAndView newIncome(WebRequest request) {
        String accountId = request.getParameter("accountId");
        User user = userService.getLoggedUser();
        List<OutcomeType> availableOutcomeTypes = outcomeTypeService.getAvailableOutcomeTypes(user);
        OutcomeDto outcomeDto = new OutcomeDto();
        outcomeDto.setAccountId(Long.parseLong(accountId));
        outcomeDto.setOutcomeTypes(availableOutcomeTypes);
        return new ModelAndView("newoutcome", "outcomeDto", outcomeDto);
    }

    @RequestMapping(value = "/addoutcome", method = RequestMethod.POST)
    public ModelAndView addIncome(@ModelAttribute("outcomeDto") OutcomeDto outcomeDto, BindingResult result) {
        User user = userService.getLoggedUser();
        if (/*!result.hasErrors() && */user != null) {
            Account account = accountService.findAccountById(outcomeDto.getAccountId());
            OutcomeType outcomeType = outcomeTypeService.findTypeById(outcomeDto.getOutcomeTypeId());
            if (account != null && outcomeType != null) {
                outcomeService.addOutcome(outcomeDto, account, outcomeType);
                account.setBalance(account.getBalance().subtract(new BigDecimal(outcomeDto.getAmount())));
                accountService.updateAccount(account);
                return new ModelAndView("redirect:" + "/index");
            }
        }
        return new ModelAndView("newoutcome");
    }


}
