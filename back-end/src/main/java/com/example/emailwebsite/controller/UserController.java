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

    private static String userEmailAddress = null;

    @Autowired
    public UserController(UserService userService, EmailsService emailsService) {
        this.userService = userService;
        this.emailsService = emailsService;
    }

    @GetMapping("/user/index/{emailAddress}")
    public String userIndex(@PathVariable String emailAddress, Model model) {
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
        String senderName = userService.getUserNameByEmailAddress(senderEmailAddress);
        emailsService.save(email, senderName);
        return "redirect:/user/index/" +senderEmailAddress;

    }
    @GetMapping("/user/{emailAddress}/sent")
    public String userSent(@PathVariable String emailAddress, Model model) {
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
    @GetMapping("/user/{emailAddress}/important")
    public String userImportantEmails(@PathVariable String emailAddress, Model model) {
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

    @GetMapping("/user/{emailAddress}/trash")
    public String userTrashEmails(@PathVariable String emailAddress, Model model) {
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
    @GetMapping("/user/{emailAddress}/setting")
    public String setting(@PathVariable String emailAddress, Model model) {
        return "setting";
    }

    @PostMapping("/user/updateStatus")
    public String updateStatus(@RequestParam Integer emailId, @RequestParam String status) {
        String senderEmailAddress = userEmailAddress;
        emailsService.updateEmailStatus(emailId, status);
        return "redirect:/user/index/" +senderEmailAddress;
    }
    @GetMapping("/user/{emailAddress}/search")
    public String search(Model model, @PathVariable("emailAddress") String emailAddress,@RequestParam String keyWord){
        List<Emails> emailsList;
        userEmailAddress = emailAddress;
        Long id = userService.findByEmail(emailAddress).getAccount_id();
        if(keyWord != null){
            emailsList = emailsService.getEmailsByKeyWord(keyWord);
            if(emailsList.isEmpty()){
                emailsList = emailsService.getEmailsReceivedByUserId(id);
                model.addAttribute("userName", emailAddress);
                model.addAttribute("error", "Can not find the email!");

            } else {
                model.addAttribute("success", "Search successfully!");
            }
        }
        else {
            emailsList = emailsService.getEmailsReceivedByUserId(id);
        }
        model.addAttribute("emails", emailsList);
        return "redirect:/user/"+ emailAddress+ "/sent" ;
    }

    @GetMapping("/user/emailDetails/{emailId}")
    public String viewEmailDetails(@PathVariable Integer emailId, Model model) {
        String emailAddress = emailsService.getEmailAddressById(emailId);
        Emails email = emailsService.getEmailById(emailId);
        model.addAttribute("userName", emailAddress);
        model.addAttribute("email", email);
        return "emailDetail"; // Create a corresponding HTML template for email details
    }
}