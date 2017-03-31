package com.epam.internal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Welcome {

    @RequestMapping("/welcome")
    @ResponseBody
    public String welcome() {
        return "Hello";
    }
}