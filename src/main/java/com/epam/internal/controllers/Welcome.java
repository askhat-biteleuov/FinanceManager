package com.epam.internal.controllers;

import com.epam.internal.daos.UserDao;
import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;
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
        return userDao.getUserByEmail("user@email").getEmail();
    }
}