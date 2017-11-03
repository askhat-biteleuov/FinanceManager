package com.fm.internal.validation;

import com.fm.internal.dtos.PasswordDto;
import com.fm.internal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PasswordValidator implements Validator {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public boolean supports(Class<?> clazz) {
        return PasswordDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordDto passwordDto = (PasswordDto) target;
        String userPassword = userService.getLoggedUser().getPassword();
        if (!encoder.matches(passwordDto.getOldPassword().trim(), userPassword)) {
            errors.rejectValue("oldPassword", "NotEquals");
        } else {
            if (!passwordDto.getPassword().equals(passwordDto.getConfirm())) {
                errors.rejectValue("password", "NotEquals");
            }
        }
    }
}
