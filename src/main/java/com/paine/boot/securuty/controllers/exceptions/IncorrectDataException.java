package com.paine.boot.securuty.controllers.exceptions;


import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.stereotype.Component;

import com.paine.boot.securuty.entities.User;


@Component
public class IncorrectDataException {

    public void throwException(User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        bindingResult.addError(new FieldError(bindingResult.getObjectName(),
                "email", "Email must be unique"));
        redirectAttributes.addFlashAttribute("user", user);
        redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
    }
}