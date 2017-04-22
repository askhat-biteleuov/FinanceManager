package com.fm.internal.controllers;

import com.fm.internal.dtos.TransferDto;
import com.fm.internal.services.AccountService;
import com.fm.internal.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class TransferController {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    public ModelAndView getTransferList(WebRequest request) {
        String accountId = request.getParameter("accountId");
        TransferDto transferDto = new TransferDto();
        transferDto.setAccountId(Long.parseLong(accountId));
        ModelAndView modelAndView = new ModelAndView("transfer");
        modelAndView.addObject("transferDto", transferDto);
        modelAndView.addObject("accounts",
                accountService.findAllUserAccounts(accountService.findAccountById(transferDto.getAccountId()).getUser()));
        return modelAndView;
    }

    @RequestMapping(value = "/transfer", method =  RequestMethod.POST)
    public ModelAndView submitTransfer(@Valid @ModelAttribute("transferDto") TransferDto transferDto, BindingResult result) {
        if (!result.hasErrors()) {
            accountService.makeTransfer(accountService.findAccountById(transferDto.getAccountId()),
                    accountService.findAccountById(transferDto.getToAccountId()), transferDto.getAmount());
            return new ModelAndView("redirect:" + "/index");
        }
        ModelAndView modelAndView = new ModelAndView("transfer");
        modelAndView.addObject("accounts",
                accountService.findAllUserAccounts(accountService.findAccountById(transferDto.getAccountId()).getUser()));
        return modelAndView;
    }

}
