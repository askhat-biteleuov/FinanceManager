package com.fm.internal.controllers;

import com.fm.internal.dtos.AvatarDto;
import com.fm.internal.dtos.RegistrationDto;
import com.fm.internal.models.User;
import com.fm.internal.models.UserInfo;
import com.fm.internal.services.CurrencyService;
import com.fm.internal.services.StatusBarService;
import com.fm.internal.services.UserService;
import com.fm.internal.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private UserService userService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private StatusBarService statusBarService;
    @Autowired
    private UserValidator validator;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getProfilePage() {
        return getModelAndView();
    }

    @RequestMapping(value = "/avatar", method = RequestMethod.POST)
    @ResponseBody
    public Object addNewAvatar(@RequestBody AvatarDto avatarDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateUser(@ModelAttribute RegistrationDto registrationDto,
                                   BindingResult result) {
        User loggedUser = userService.getLoggedUser();
        UserInfo info = loggedUser.getInfo();
        info.setFirstName(registrationDto.getFirstName());
        info.setLastName(registrationDto.getLastName());
        loggedUser.setInfo(info);
        userService.updateUser(loggedUser);
        return getProfilePage();
    }

    private ModelAndView getModelAndView() {
        User user = userService.getLoggedUser();
        ModelAndView modelAndView = new ModelAndView("profilepage");
        modelAndView.addObject("user", user);
        modelAndView.addObject("userInfo", user.getInfo());
        modelAndView.addObject("currencies", currencyService.getCurrencies());
        modelAndView.addObject("statusBarDto", statusBarService.getStatusBar(user));
        return modelAndView;
    }
}
