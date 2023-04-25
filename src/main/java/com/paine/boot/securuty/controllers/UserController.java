package com.paine.boot.securuty.controllers;


import com.paine.boot.securuty.services.AppService;
import com.paine.boot.securuty.entities.User;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UserController {
    private final AppService appService;

    @Autowired
    public UserController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping
    public String showUserPage(Principal principal, Model model) {
        User user = appService.findByUsername(principal.getName());
        model.addAttribute("authorizedUser", user);
        return "user-page";
    }
}
