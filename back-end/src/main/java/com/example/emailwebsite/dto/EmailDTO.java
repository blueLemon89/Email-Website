package com.example.emailwebsite.dto;

import com.example.emailwebsite.entity.Emails;
import lombok.Data;

@Data
public class EmailDTO {
    private String label;

    private String body;

    private String senderName;

    private String recipientName;

    private String subject;

    private String timeSent;

}
