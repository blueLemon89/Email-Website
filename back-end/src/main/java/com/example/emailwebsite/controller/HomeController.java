package com.example.emailwebsite.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/login")
    public String showLogin(){
        return "login";
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/login";
    }

}
