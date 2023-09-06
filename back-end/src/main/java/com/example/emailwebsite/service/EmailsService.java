package com.example.emailwebsite.service;

import com.example.emailwebsite.entity.Emails;
import com.example.emailwebsite.repository.EmailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailsService {

    private final EmailsRepository emailsRepository;
    @Autowired
    public EmailsService(EmailsRepository emailsRepository) {
        this.emailsRepository = emailsRepository;
    }

    public List<Emails> getAllEmailsByUserName(String userName){
        return emailsRepository.getAllEmailByUserName();
    }
}

