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

    @GetMapping
    public String home(){
        return "redirect:/login";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/user/emaills")
    public String userIndex(Model model){
        String message = "Access is successful";
        model.addAttribute("msg",message);
        return "index";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/admin/dashboard")
    public String admin(Model model){
        String message = "Access is successful";
        model.addAttribute("msg",message);
        return "dashboard";
    }
    @GetMapping("/denied")
    public String deniedLogin(){
        return "denied";
    }
}
