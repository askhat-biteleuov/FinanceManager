package com.epam.internal.controllers;

import com.epam.internal.dao.test.GenericDao;
import com.epam.internal.dao.test.UserDao;
import com.epam.internal.data.entities.User;
import com.epam.internal.data.entities.UserInfo;
import com.epam.internal.service.UserService;
import com.epam.internal.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Welcome {
    @Autowired
    UserDao userDao;
    @RequestMapping("/welcome")
    @ResponseBody
    public String welcome() {
        User user = new User("user@email","password",new UserInfo("name","lastname"));
        userDao.create(user);
        return (userDao.getUserByEmail("user@email")).getEmail();
    }
}