package com.example.emailwebsite.entity;

import com.example.emailwebsite.dto.Label;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "emails")
public class Emails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "emailId")
    private Integer emailId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @NonNull
    private String senderName;
    @NonNull
    private String recipientName;

    private String subject;

    private String body;
    @NonNull
    private Label label;
    @NonNull
    private String timeSend;
//    private Boolean isSchedule;
}
