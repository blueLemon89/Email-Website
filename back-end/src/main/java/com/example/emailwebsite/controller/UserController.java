package com.example.emailwebsite.controller;

import com.example.emailwebsite.dto.RegisterDto;
import com.example.emailwebsite.entity.Account;
import com.example.emailwebsite.entity.Emails;
import com.example.emailwebsite.service.EmailsService;
import com.example.emailwebsite.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Controller
public class UserController {
    @Lazy
    private final UserService userService;

    @Lazy
    private final EmailsService emailsService;

    private String userEmailAddress;

    @Autowired
    public UserController(UserService userService, EmailsService emailsService) {
        this.userService = userService;
        this.emailsService = emailsService;
    }

    public void constructor(String name){
        this.userEmailAddress = name;
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
    @GetMapping("/user/index")
    public String userIndex(@RequestParam("emailAddress") String emailAddress, Model model) {
        userEmailAddress = emailAddress;
        Long id = userService.findByEmail(emailAddress).getAccount_id();
        // Get received email ?
        List<Emails> emails = emailsService.getEmailsReceivedByUserId(id);
        String defaultStatus = emails.isEmpty() ? "None" : emails.get(0).getStatus();
        model.addAttribute("emails", emails);
        model.addAttribute("userName", emailAddress);
        model.addAttribute("defaultStatus", defaultStatus);
        return "emails";
    }

    @GetMapping("/user/compose")
    public String getCompose(Model model){
        Emails emails = new Emails();
        model.addAttribute("emails", emails);
        return "compose";
    }
    @PostMapping("/user/index")
    public String composeEmail(@ModelAttribute("email") Emails email) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String senderEmailAddress = authentication.getName();
        emailsService.save(email, senderEmailAddress);
//        model.addAttribute("email", senderEmailAddress);
        return "redirect:/user/index?emailAddress=" +senderEmailAddress;

    }
    @GetMapping("/user/sent")
    public String userSent(@RequestParam("emailAddress") String emailAddress, Model model) {
        userEmailAddress = emailAddress;
        Long id = userService.findByEmail(emailAddress).getAccount_id();
        // Get sent email ?
        List<Emails> emails = emailsService.getEmailsSentByUserId(id);
        String defaultStatus = emails.isEmpty() ? "None" : emails.get(0).getStatus();
        //List all
//        List<Emails> emails = emailsService.getAllEmailByUserId(id);
        model.addAttribute("emails", emails);
        model.addAttribute("userName", emailAddress);
        model.addAttribute("defaultStatus", defaultStatus);
        return "sentEmails";
    }
    @GetMapping("/user/important")
    public String userImportantEmails(@RequestParam("emailAddress") String emailAddress, Model model) {
        userEmailAddress = emailAddress;
        Long id = userService.findByEmail(emailAddress).getAccount_id();
        // Get important email ?
        List<Emails> emails = emailsService.getEmailsImportantByUserId(id);
        String defaultStatus = emails.isEmpty() ? "None" : emails.get(0).getStatus();
        //List all
//        List<Emails> emails = emailsService.getAllEmailByUserId(id);
        model.addAttribute("emails", emails);
        model.addAttribute("userName", emailAddress);
        model.addAttribute("defaultStatus", defaultStatus);
        return "importantEmails";
    }

    @GetMapping("/user/trash")
    public String userTrashEmails(@RequestParam("emailAddress") String emailAddress, Model model) {
        userEmailAddress = emailAddress;
        Long id = userService.findByEmail(emailAddress).getAccount_id();
        // Get important email ?
        List<Emails> emails = emailsService.getEmailsTrashByUserId(id);
        String defaultStatus = emails.isEmpty() ? "None" : emails.get(0).getStatus();
        model.addAttribute("emails", emails);
        model.addAttribute("userName", emailAddress);
        model.addAttribute("defaultStatus", defaultStatus);
        return "trashEmails";
    }
    @GetMapping("/user/setting")
    public String setting(@RequestParam("emailAddress") String emailAddress, Model model) {

        return "setting";
    }

    @PostMapping("/user/updateStatus")
    public String updateStatus(@RequestParam Integer emailId, @RequestParam String status) {
        String senderEmailAddress = userEmailAddress;
        emailsService.updateEmailStatus(emailId, status);
        return "redirect:/user/index?emailAddress=" +senderEmailAddress;
    }

}