package com.fm.internal.controllers;

import com.fm.internal.dtos.AvatarDto;
import com.fm.internal.dtos.PasswordDto;
import com.fm.internal.dtos.RegistrationDto;
import com.fm.internal.models.User;
import com.fm.internal.models.UserInfo;
import com.fm.internal.services.CurrencyService;
import com.fm.internal.services.StatusBarService;
import com.fm.internal.services.UserService;
import com.fm.internal.validation.PasswordValidator;
import com.fm.internal.validation.util.ValidErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

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
    private PasswordValidator passwordValidator;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private MessageSource messages;

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
        info.setCurrency(currencyService.findCurrencyByCharCode(registrationDto.getCurrency()));
        loggedUser.setInfo(info);
        userService.updateUser(loggedUser);
        return getProfilePage();
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    @ResponseBody
    public Object updatePassword(@RequestBody PasswordDto passwordDto, BindingResult result) {
        passwordValidator.validate(passwordDto, result);
        if (result.hasErrors()) {
            Map<String, String> errors = ValidErrors.getMapOfMessagesAndErrors(result, messages);
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        } else {
            User loggedUser = userService.getLoggedUser();
            loggedUser.setPassword(encoder.encode(passwordDto.getPassword()));
            userService.updateUser(loggedUser);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView updateByGet() {
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
