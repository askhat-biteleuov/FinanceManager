package com.fm.internal.validation;

import com.fm.internal.dtos.OutcomeTypeDto;
import com.fm.internal.models.OutcomeType;
import com.fm.internal.services.OutcomeTypeService;
import com.fm.internal.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

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
        availableOutcomeTypes.forEach(outcomeType -> LOGGER.info(outcomeType.getName()));
        boolean anyMatch = availableOutcomeTypes.stream()
                .map(OutcomeType::getName)
                .anyMatch(typeName -> typeName.equals(dto.getName()));
        LOGGER.info(anyMatch);
        if (anyMatch) errors.rejectValue("name", "Exist");
    }
}
