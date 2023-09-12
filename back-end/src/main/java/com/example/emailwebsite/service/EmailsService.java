package com.example.emailwebsite.service;

import com.example.emailwebsite.dto.Label;
import com.example.emailwebsite.entity.Account;
import com.example.emailwebsite.entity.Emails;
import com.example.emailwebsite.repository.AccountRepository;
import com.example.emailwebsite.repository.EmailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public void save(Emails emails, String senderName) throws ParseException {
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

        Timestamp getDate = new Timestamp(date.getTime());

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");

        Date newDate = inputFormat.parse(getDate.toString());
        String result = outputFormat.format(newDate);

        if(emails.getRecipientName() != null){
            newEmailSent.setBody(emails.getBody());
            newEmailSent.setSubject(emails.getSubject());
            newEmailSent.setSenderName(senderName);
            newEmailSent.setRecipientName(emails.getRecipientName());
            newEmailSent.setTimeSend(result);
            newEmailSent.setAccount(accountSent);
            newEmailSent.setLabel(Label.SENT);
            newEmailSent.setStatus("None");

            newEmailReceive.setBody(emails.getBody());
            newEmailReceive.setSubject(emails.getSubject());
            newEmailReceive.setSenderName(senderName);
            newEmailReceive.setRecipientName(emails.getRecipientName());
            newEmailReceive.setTimeSend(result);
            newEmailReceive.setAccount(accountReceive);
            newEmailReceive.setLabel(Label.RECEIVED);
            newEmailReceive.setStatus("None");
        }
        emailsRepository.save(newEmailSent);
        emailsRepository.save(newEmailReceive);
    }

    public List<Emails> getEmailsReceivedByUserId(Long id) {
        return emailsRepository.getEmailsReceivedByUserId(id);
    }

    public List<Emails> getEmailsSentByUserId(Long id) {
        return emailsRepository.getEmailsSentByUserId(id);
    }

    public List<Emails> getEmailsImportantByUserId(Long id) {
        return emailsRepository.getEmailsImportantByUserId(id);
    }

    public List<Emails> getEmailsTrashByUserId(Long id){
        return emailsRepository.getEmailsTrashByUserId(id);
    }

    public void updateEmailStatus(Integer emailId, String status) {
        Emails email = emailsRepository.findById(emailId).orElse(null);

        if (email != null) {
            email.setStatus(status);
            emailsRepository.save(email);
        }
    }
}

