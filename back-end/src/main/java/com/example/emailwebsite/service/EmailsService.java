package com.example.emailwebsite.service;

import com.example.emailwebsite.dto.Label;
import com.example.emailwebsite.entity.Account;
import com.example.emailwebsite.entity.Emails;
import com.example.emailwebsite.repository.AccountRepository;
import com.example.emailwebsite.repository.EmailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class EmailsService {

    private final EmailsRepository emailsRepository;

    private final AccountRepository accountRepository;
    @Autowired
    public EmailsService(EmailsRepository emailsRepository, AccountRepository accountRepository) {
        this.emailsRepository = emailsRepository;
        this.accountRepository = accountRepository;
    }

    public List<Emails> getAllEmailByUserId(Long id){
        return emailsRepository.getAllEmailByUserId(id);
    }

    public void save(Emails emails, String senderName){
        Emails newEmailSent = new Emails();
        Emails newEmailReceive = new Emails();

//        Long accId = accountRepository.getIdByEmailAddress(senderName);
        Account accountSent = accountRepository.findByEmailAddress(senderName);
        Account accountReceive = new Account();
        if(emails.getRecipientName() != null) {
            String emailAddressReceive = accountRepository.findEmailAddressByUserName(emails.getRecipientName());
            accountReceive = accountRepository.findByEmailAddress(emailAddressReceive);
        }
        Date date = new Date();

        if(emails.getRecipientName() != null){
            newEmailSent.setBody(emails.getBody());
            newEmailSent.setSubject(emails.getSubject());
            newEmailSent.setSenderName(senderName);
            newEmailSent.setRecipientName(emails.getRecipientName());
            newEmailSent.setTimeSend(new Timestamp(date.getTime()));
            newEmailSent.setAccount(accountSent);
            newEmailSent.setLabel(Label.SENT);

            newEmailReceive.setBody(emails.getBody());
            newEmailReceive.setSubject(emails.getSubject());
            newEmailReceive.setSenderName(senderName);
            newEmailReceive.setRecipientName(emails.getRecipientName());
            newEmailReceive.setTimeSend(new Timestamp(date.getTime()));
            newEmailReceive.setAccount(accountReceive);
            newEmailReceive.setLabel(Label.RECEIVED);
        }
        emailsRepository.save(newEmailSent);
        emailsRepository.save(newEmailReceive);
    }
}

