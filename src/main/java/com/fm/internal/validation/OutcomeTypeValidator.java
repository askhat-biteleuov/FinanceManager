package com.fm.internal.validation;

import com.fm.internal.dtos.OutcomeTypeDto;
import com.fm.internal.models.OutcomeType;
import com.fm.internal.services.OutcomeTypeService;
import com.fm.internal.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class OutcomeTypeValidator implements Validator {
    private static final Logger LOGGER = Logger.getLogger(OutcomeTypeValidator.class);

    @Autowired
    private OutcomeTypeService outcomeTypeService;

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return OutcomeTypeDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OutcomeTypeDto dto = (OutcomeTypeDto) target;
        List<OutcomeType> availableOutcomeTypes = outcomeTypeService.getAvailableOutcomeTypes(userService.getLoggedUser());
        boolean anyMatch = false;
        if (dto.getId() != 0) {
            for (OutcomeType outcomeType : availableOutcomeTypes) {
                anyMatch = (outcomeType.getId() != dto.getId()) && (outcomeType.getName().equalsIgnoreCase(dto.getName()));
            }
        } else {
            anyMatch = availableOutcomeTypes.stream()
                    .map(OutcomeType::getName)
                    .anyMatch(typeName -> typeName.equalsIgnoreCase(dto.getName()));
        }
        if (anyMatch) errors.rejectValue("name", "Exist");
    }
}
