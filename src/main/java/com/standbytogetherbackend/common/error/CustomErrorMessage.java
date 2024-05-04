package com.standbytogetherbackend.common.error;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public class CustomErrorMessage {

    private Map<String, String> getErrorMessage(FieldError fieldError) {
        String defaultMessage = fieldError.getDefaultMessage();
        if (defaultMessage != null) {
            return Map.of(fieldError.getField(), defaultMessage);
        }
        return null;
    }

    public List<Map<String, String>> getErrorMessages(BindingResult bindingResult) {
        List<Map<String, String>> errorMessages = new ArrayList<>();
        bindingResult.getAllErrors().forEach(fieldError -> {
            errorMessages.add(getErrorMessage((FieldError) fieldError));
        });
        return errorMessages;
    }
}
