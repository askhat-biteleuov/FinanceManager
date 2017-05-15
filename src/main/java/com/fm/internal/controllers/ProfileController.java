package com.fm.internal.controllers;

import com.fm.internal.dtos.AvatarDto;
import com.fm.internal.models.User;
import com.fm.internal.services.CurrencyService;
import com.fm.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private UserService userService;
    @Autowired
    private CurrencyService currencyService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getProfilePage() {
        User user = userService.getLoggedUser();
        ModelAndView modelAndView = new ModelAndView("profilepage");
        modelAndView.addObject("user", user);
        modelAndView.addObject("userInfo", user.getInfo());
        modelAndView.addObject("currencies", currencyService.getCurrencies());
        return modelAndView;
    }

    @RequestMapping("/avatar")
    @ResponseBody
    public byte[] addNewAvatar(@RequestBody AvatarDto avatarDto) {
        return null;
    }
}
