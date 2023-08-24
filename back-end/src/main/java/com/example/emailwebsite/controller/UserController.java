package com.example.emailwebsite.controller;

import com.example.emailwebsite.dto.UserDTO;
import com.example.emailwebsite.entity.User;
import com.example.emailwebsite.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("register")
    public String showRegistrationForm(Model model){
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDTO userDTO,
                               BindingResult result,
                               Model model){
        User userExisting = userService.getUserByUsername(userDTO.getEmailAddress());
        if (userExisting != null) {
            result.rejectValue("emailAddress", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("userDTO", userDTO);
            return "register";
        }
        userService.addUser(userDTO);
        return "redirect:/register?success";
    }

    @GetMapping("/get/{userName}")
    public ResponseEntity<Object> getUserByUsername(@PathVariable String userName) {
        try {
            User user = userService.getUserByUsername(userName);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/getAll")
    public ResponseEntity<Object> getAll(){
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
        try {
            userService.addUser(userDTO);
            return ResponseEntity.ok("User added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add user.");
        }
    }
}
