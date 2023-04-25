package com.paine.boot.securuty.controllers;


import com.paine.boot.securuty.controllers.exceptions.IncorrectDataException;
import com.paine.boot.securuty.entities.User;
import com.paine.boot.securuty.services.AppService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;

import jakarta.validation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AppService appService;
    private final IncorrectDataException incorrectDataException;

    @Autowired
    public AdminController(AppService appService, IncorrectDataException incorrectDataException) {
        this.appService = appService;
        this.incorrectDataException = incorrectDataException;
    }

    @GetMapping
    public String showAdminPage(Principal principal, Model model) {
        User admin = appService.findByUsername(principal.getName());
        model.addAttribute("authorizedAdmin", admin)
                .addAttribute("users", appService.findAllUsers())
                .addAttribute("roles", appService.findAllRoles())
                .addAttribute("showUserProfile",
                model.containsAttribute("user") && !((User) model.getAttribute("user")).isNew())
                .addAttribute("showNewUserForm",
                model.containsAttribute("user") && ((User) model.getAttribute("user")).isNew());
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "admin-page";
    }

    @GetMapping("/{id}/profile")
    public String showUserProfileModal(@PathVariable("id") Long id, Model model) {
        model.addAttribute("roles", appService.findAllRoles());
        model.addAttribute("user", appService.findUserById(id));
        return "fragments/user-form";
    }

    @PatchMapping
    public String saveOrUpdateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasErrors()) {
            try {
                appService.addOrUpdateUser(user);

            } catch (DataIntegrityViolationException ex) {
                incorrectDataException.throwException(user, bindingResult, redirectAttributes);
            }
        } else {
            incorrectDataException.throwException(user, bindingResult, redirectAttributes);
        }
        return "redirect:/admin";
    }

    @DeleteMapping
    public String deleteUser(@ModelAttribute("user") User user) {
        appService.deleteUser(user.getId());
        return "redirect:/admin";
    }
}
