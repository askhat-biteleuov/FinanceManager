package com.epam.internal.controllers;

import com.epam.internal.models.Account;
import com.epam.internal.models.OutcomeType;
import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;
import com.epam.internal.services.AccountService;
import com.epam.internal.services.OutcomeTypeService;
import com.epam.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

@Controller
public class InitController {
    @Autowired
    UserService userService;
    @Autowired
    AccountService accountService;
    @Autowired
    OutcomeTypeService outcomeTypeService;

    @RequestMapping("/init")
    public ModelAndView init() {
        User user = new User("user@email", "password", new UserInfo("name", "lastname"));
        userService.createUser(user);

        Account[] accounts = {
                new Account("Visa", new BigDecimal(29999), null, user),
                new Account("Mastercard", new BigDecimal(3000), null, user),
                new Account("Кошелек", new BigDecimal(5752), null, user)
        };
        for (Account account : accounts) {
            accountService.createAccount(account);
        }

        OutcomeType[] types = {
                new OutcomeType("Еда вне дома", new BigDecimal(3000), user),
                new OutcomeType("Транспорт", new BigDecimal(500), user),
                new OutcomeType("Интернет", new BigDecimal(1700), user),
                new OutcomeType("Развлечения", new BigDecimal(3000), user)
        };
        for (OutcomeType type : types) {
            outcomeTypeService.addOutcomeType(type);
        }
        return new ModelAndView("index");
    }
}
