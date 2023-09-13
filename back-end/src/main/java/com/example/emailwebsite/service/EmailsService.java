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
import java.util.Base64;
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
        Account accountSent = accountRepository.findByUserName(senderName);
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
            String encodedBody = Base64.getEncoder().encodeToString(emails.getBody().getBytes());
            String encodedSubject = Base64.getEncoder().encodeToString(emails.getSubject().getBytes());

            newEmailSent.setBody(encodedBody);
            newEmailSent.setSubject(encodedSubject);
            newEmailSent.setSenderName(senderName);
            newEmailSent.setRecipientName(emails.getRecipientName());
            newEmailSent.setTimeSend(result);
            newEmailSent.setAccount(accountSent);
            newEmailSent.setLabel(Label.SENT);
            newEmailSent.setStatus("None");

            newEmailReceive.setBody(encodedBody);
            newEmailReceive.setSubject(encodedSubject);
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
        List<Emails> receivedEmails = emailsRepository.getEmailsReceivedByUserId(id);

        // Decode body and subject for each received email
        decodeEmails(receivedEmails);
        return receivedEmails;
    }

    public List<Emails> getEmailsSentByUserId(Long id) {
        List<Emails> sentEmails = emailsRepository.getEmailsSentByUserId(id);
        // Decode body and subject for each received email
        decodeEmails(sentEmails);
        return sentEmails;
    }

    public List<Emails> getEmailsImportantByUserId(Long id) {
        List<Emails> importantEmails = emailsRepository.getEmailsImportantByUserId(id);
        decodeEmails(importantEmails);
        return importantEmails;
    }

    public List<Emails> getEmailsTrashByUserId(Long id){
        List<Emails> trashEmails = emailsRepository.getEmailsTrashByUserId(id);
        decodeEmails(trashEmails);
        return trashEmails;
    }

    public void updateEmailStatus(Integer emailId, String status) {
        Emails email = emailsRepository.findById(emailId).orElse(null);

        if (email != null) {
            email.setStatus(status);
            emailsRepository.save(email);
        }
    }

    public List<Emails> getAllEmails(String status, String label) {
        return emailsRepository.getAll(status, label);
    }


    public List<Emails> getEmailsByKeyWord(String keyWord) {
        return emailsRepository.getEmailsByKeyWord(keyWord);
    }

    private void decodeEmails(List<Emails> emailList){
        for (Emails email : emailList) {
            String decodedBody = new String(Base64.getDecoder().decode(email.getBody()));
            String decodedSubject = new String(Base64.getDecoder().decode(email.getSubject()));
            email.setBody(decodedBody);
            email.setSubject(decodedSubject);
        }
    }

    public Emails getEmailById(Integer emailId) {
        Emails email = emailsRepository.getEmailById(emailId);
        String decodedBody = new String(Base64.getDecoder().decode(email.getBody()));
        String decodedSubject = new String(Base64.getDecoder().decode(email.getSubject()));
        email.setBody(decodedBody);
        email.setSubject(decodedSubject);
        return email;
    }


    public String getEmailAddressById(Integer emailId) {
        return emailsRepository.getEmailAddressById(emailId);
    }
}

