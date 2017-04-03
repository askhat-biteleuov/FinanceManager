package com.epam.internal.controllers;

import com.epam.internal.daos.UserDao;
import com.epam.internal.models.Account;
import com.epam.internal.models.Income;
import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;
import com.epam.internal.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Date;

@Controller
public class Welcome {
    @Autowired
    UserDao userDao;

    @Autowired
    private IncomeService service;
    @RequestMapping("/welcome")
    @ResponseBody
    public String welcome() {
       // User user = new User("user@email","password",new UserInfo("name","lastname"));
       // userDao.create(user);
        return "das";//userDao.getUserByEmail("user@email").getEmail();
    }

    @RequestMapping("/users")
    public ModelAndView users() {
        ModelAndView model = new ModelAndView("users");
        User user = new User("user@email","password",new UserInfo("name","lastname"));
        userDao.create(user);
        model.addObject("user", userDao.getUserByEmail("user@email"));
        return model;
    }

    @RequestMapping("/income")
    @ResponseBody
    public String income() {
        User user = new User("user@email","password",new UserInfo("name","lastname"));
        Account account = new Account("card", BigDecimal.valueOf(5000), null, user);
        Income income = new Income(BigDecimal.valueOf(300), new Date(), account);
        service.addIncome(income);
        return service.findById(1L).getAmount().toString();
    }
}