package com.example.emailwebsite.controller;

import com.example.emailwebsite.dto.RegisterDto;
import com.example.emailwebsite.entity.Account;
import com.example.emailwebsite.entity.Emails;
import com.example.emailwebsite.service.EmailsService;
import com.example.emailwebsite.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    @Lazy
    private final UserService userService;

    @Lazy
    private final EmailsService emailsService;

    @Autowired
    public UserController(UserService userService, EmailsService emailsService) {
        this.userService = userService;
        this.emailsService = emailsService;
    }

    @GetMapping("/register")
    public String regis(Model model) {
        RegisterDto registerDto = new RegisterDto();
        model.addAttribute("register", registerDto);
        return "register";
    }

    @PostMapping("/register/user")
    public String saveUser(@Valid @ModelAttribute("register")
                           RegisterDto registerDto, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("register", registerDto);
            return "register";
        }
        Account existingUser = userService.findByEmail(registerDto.getEmail());
        if (existingUser != null) {
            bindingResult.rejectValue("email", null, "User already use!!" + existingUser);
        }
        userService.save(registerDto);
        return "redirect:/login";
    }
    @GetMapping("/user/{emailAddress}")
    public String userPage(@PathVariable String emailAddress, Model model) {
        Long id = userService.findByEmail(emailAddress).getAccount_id();
        String userName = userService.findByEmail(emailAddress).getUsername();
        List<Emails> emails = emailsService.getAllEmailByUserId(id);
        model.addAttribute("emails", emails);
        model.addAttribute("userName", userName);
        return "emails";
    }
    @PostMapping("/user/compose")
    public String composeEmail(){
        return null;
    }
    @GetMapping("/user/sent")
    public String sentEmails(){
        return null;
    }
    @GetMapping("/user/trash")
    public String trashEmails(){
        return null;
    }
    @GetMapping("/user/important")
    public String importantEmail(){
        return null;
    }
    @GetMapping("/user/setting")
    public String setting(){
        return null;
    }


}