package com.fm.internal.validation.util;

import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ValidErrors {
    public static Map<String, String> getMapOfMessagesAndErrors(BindingResult result, MessageSource messages) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : result.getFieldErrors()) {
            String[] resolveMessageCodes = result.resolveMessageCodes(fieldError.getCode());
            String string = resolveMessageCodes[0];
            String message = messages.getMessage(string + "." + fieldError.getField(), new Object[]{fieldError.getRejectedValue()}, Locale.getDefault());
            errors.put(fieldError.getField(), message);
        }
        return errors;
    }
}
