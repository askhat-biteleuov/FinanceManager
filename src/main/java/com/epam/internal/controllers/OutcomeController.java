package com.epam.internal.controllers;

import com.epam.internal.dtos.OutcomeDto;
import com.epam.internal.models.Account;
import com.epam.internal.models.Outcome;
import com.epam.internal.models.OutcomeType;
import com.epam.internal.models.User;
import com.epam.internal.services.AccountService;
import com.epam.internal.services.OutcomeService;
import com.epam.internal.services.OutcomeTypeService;
import com.epam.internal.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @RequestMapping(value = "/addoutcome", method = RequestMethod.GET)
    public ModelAndView newOutcome(WebRequest request) {
        OutcomeDto outcomeDto = prepareDTO(request);
        ModelAndView modelAndView = new ModelAndView("newoutcome", "outcomeDto", outcomeDto);
        modelAndView.addObject("types", outcomeTypeService.getAvailableOutcomeTypes(userService.getLoggedUser()));
        return modelAndView;
    }

    @RequestMapping(value = "/addoutcome", method = RequestMethod.POST)
    public ModelAndView addOutcome(@Valid @ModelAttribute("outcomeDto") OutcomeDto outcomeDto,
                                   BindingResult result,
                                   WebRequest request) {
        User user = userService.getLoggedUser();
        LOGGER.info("Нашли юзера");
        LOGGER.info(result.getAllErrors().toString());
        if (!result.hasErrors() && user != null) {
            LOGGER.info("Ошибок нет и юзер не равен нулл");
            LOGGER.info("ID Аккаунта = " + outcomeDto.getAccountId());
            LOGGER.info("ID outcomeType = " + outcomeDto.getOutcomeTypeId());
            Account account = accountService.findAccountById(outcomeDto.getAccountId());
            OutcomeType outcomeType = outcomeTypeService.findTypeById(outcomeDto.getOutcomeTypeId());
            if (account != null && outcomeType != null) {
                LOGGER.info("Аккаунт не равен нулл и тип расходов не равен нулл");
                outcomeService.addOutcome(outcomeDto, account, outcomeType);
                account.setBalance(account.getBalance().subtract(new BigDecimal(outcomeDto.getAmount())));
                accountService.updateAccount(account);
                return new ModelAndView("redirect:" + "/index");
            }
            LOGGER.info("Аккаунта или тип расходов = нулл");
        }
        LOGGER.info("юзер = нулл");
        ModelAndView modelAndView = new ModelAndView("newoutcome");
        modelAndView.addObject("types", outcomeTypeService.getAvailableOutcomeTypes(user));
        return modelAndView;
    }

    private OutcomeDto prepareDTO(WebRequest request) {
        String accountId = request.getParameter("accountId");
        OutcomeDto outcomeDto = new OutcomeDto();
        outcomeDto.setAccountId(Long.parseLong(accountId));
        return outcomeDto;
    }

    @RequestMapping(value = "/outcome/list")
    public ModelAndView listOfOutcomes(@RequestParam("accountId") int accountId) {
        ModelAndView modelAndView = new ModelAndView("outcomes-list");
        Account accountById = accountService.findAccountById(accountId);
        List<Outcome> allOutcomesInAccount = outcomeService.findAllOutcomesInAccount(accountById);
        modelAndView.addObject("outcomes", allOutcomesInAccount);
        return modelAndView;
    }

    @RequestMapping(value = "/outcome/delete", method = RequestMethod.POST)
    public ModelAndView deleteOutcome(Long outcomeId, final HttpServletRequest request) {
        Outcome outcome = outcomeService.findById(outcomeId);
        outcomeService.deleteOutcome(outcome);
        String referer = request.getHeader("referer");
        return new ModelAndView("redirect:" + referer);
    }
}
