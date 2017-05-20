package com.fm.internal.controllers;

import com.fm.internal.dtos.*;
import com.fm.internal.models.Account;
import com.fm.internal.models.User;
import com.fm.internal.services.*;
import com.fm.internal.validation.AccountValidator;
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

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Map;


@Controller
@RequestMapping("/account")
public class AccountController {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private OutcomeService outcomeService;

    @Autowired
    private OutcomeTypeService outcomeTypeService;

    @Autowired
    private AccountValidator accountValidator;

    @Qualifier("messageSource")
    @Autowired
    private MessageSource messages;
    @Autowired
    private StatusBarService statusBarService;
    @Autowired
    private HashTagService hashTagService;

//    @RequestMapping(value = "/add", method = RequestMethod.GET)
//    public ModelAndView getList() {
//        return new ModelAndView("account", "accountDto", new AccountDto());
//    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object addAccount(@RequestBody AccountDto accountDto, BindingResult result) {
        accountValidator.validate(accountDto, result);
        User loggedUser = userService.getLoggedUser();
        if (result.hasErrors() || loggedUser == null) {
            Map<String, String> errors = ValidErrors.getMapOfMessagesAndErrors(result, messages);
            return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        accountService.createAccount(accountDto, loggedUser);
        LOGGER.info("New account was added:" + accountDto.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ModelAndView getAccountPage(@RequestParam("id") long idOfAccount) {
        User loggedUser = userService.getLoggedUser();
        ModelAndView modelAndView = setDefaultMavForAccountByName(idOfAccount);
        modelAndView.setViewName("accountpage");
        modelAndView.addObject("user",loggedUser);
        modelAndView.addObject("hashtags",hashTagService.getHashTagsByUser(loggedUser));
        modelAndView.addObject("statusBarDto", statusBarService.getStatusBar(loggedUser));
        return modelAndView;
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    public Object getPieChartJson(@RequestBody RangeDto rangeDto, BindingResult result) {
        Account accountByName = accountService.findUserAccountByName(userService.getLoggedUser(), rangeDto.getAccountName());
        return outcomeTypeService.countOutcomeTypesValueByDate(accountByName,LocalDate.parse(rangeDto.getStart()),
                LocalDate.parse(rangeDto.getEnd()));
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Object editAccount(@Valid @RequestBody AccountDto accountDto, BindingResult result) {
        Account account = accountService.findAccountById(accountDto.getId());
        accountValidator.validate(accountDto, result);
        if (result.hasErrors()) {
            Map<String, String> errors = ValidErrors.getMapOfMessagesAndErrors(result, messages);
            return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        account.setName(accountDto.getName().trim());
        accountService.updateAccount(account);
        LOGGER.info("Account with id:" + accountDto.getId()+" was updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private ModelAndView setDefaultMavForAccountByName(long id) {
        ModelAndView modelAndView = new ModelAndView();
        Account account = accountService.findAccountById(id);
        RangeDto rangeDto = new RangeDto();
        rangeDto.setAccountName(account.getName());
        TransferDto transferDto = new TransferDto();
        transferDto.setAccountId(account.getId());
        modelAndView.addObject("rangeDto", rangeDto);
        modelAndView.addObject("account", account);
        modelAndView.addObject("incomeDto", new IncomeDto());
        modelAndView.addObject("outcomeDto", new OutcomeDto());
        modelAndView.addObject("transferDto", transferDto);
        modelAndView.addObject("types", outcomeTypeService.getAvailableOutcomeTypes(userService.getLoggedUser()));
        modelAndView.addObject("accounts",
                accountService.findAllUserAccounts(accountService.findAccountById(transferDto.getAccountId()).getUser()));
        modelAndView.addObject("outcomes", outcomeTypeService.defaultOutcomeTypesValue(account));
        return modelAndView;
    }
}
