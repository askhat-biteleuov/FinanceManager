package com.epam.internal.controllers;

import com.epam.internal.dtos.OutcomeDto;
import com.epam.internal.models.Account;
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
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

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
        return new ModelAndView("newoutcome", "outcomeDto", outcomeDto);
    }

    @RequestMapping(value = "/addoutcome", method = RequestMethod.POST)
    public ModelAndView addOutcome(@ModelAttribute("outcomeDto") OutcomeDto outcomeDto, BindingResult result, WebRequest request) {
        User user = userService.getLoggedUser();
        LOGGER.info("Нашли юзера");
        if (/*!result.hasErrors() &&*/ user != null) {
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
        OutcomeDto freshDto = prepareDTO(request);
        return new ModelAndView("newoutcome", "outcomeDto", freshDto);
    }

    public OutcomeDto prepareDTO(WebRequest request) {
        String accountId = request.getParameter("accountId");
        User user = userService.getLoggedUser();
        List<OutcomeType> availableOutcomeTypes = outcomeTypeService.getAvailableOutcomeTypes(user);
        OutcomeDto outcomeDto = new OutcomeDto();
        outcomeDto.setAccountId(Long.parseLong(accountId));
        outcomeDto.setOutcomeTypes(availableOutcomeTypes);
        availableOutcomeTypes.forEach(type -> LOGGER.info(type.getId()));
        return outcomeDto;
    }


}
