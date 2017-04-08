package com.epam.internal.validation;

import com.epam.internal.dtos.RegistrationDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return RegistrationDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegistrationDto registrationDto = (RegistrationDto) o;

        if (!registrationDto.getPassword().equals(registrationDto.getConfirm())) {
            errors.rejectValue("password", "NotEquals.registrationDto.password");
        }
    }

}
