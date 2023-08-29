package com.example.emailwebsite.controller;

import com.example.emailwebsite.dto.RegisterDto;
import com.example.emailwebsite.entity.Account;
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

@Controller
public class UserController {
    @Lazy
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
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
        Account existingUser = service.findByEmail(registerDto.getEmail());
        if (existingUser != null) {
            bindingResult.rejectValue("email", null, "User already use!!" + existingUser);
        }
        service.save(registerDto);
        return "redirect:/login";
    }

    @GetMapping("/user/{emailAddress}")
    public String userPage(@PathVariable String emailAddress, Model model) {
        // Here you can retrieve user-specific data and add it to the model
        // This data can then be displayed in the user page template
        String userName = service.findByEmail(emailAddress).getUsername();
        model.addAttribute("userName", userName);
        return "emailsOfUser"; // Return the name of the user page template
    }
    @PostMapping("/user/{emailAddress}/compose")
    public String composeEmail(){
        return null;
    }
    @GetMapping("user/{emailAdress}/sent")
    public String sentEmails(){
        return null;
    }
    @GetMapping("/user/{emailAddress}/trash")
    public String trashEmails(){
        return null;
    }


}