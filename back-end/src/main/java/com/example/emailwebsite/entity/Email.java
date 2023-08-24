package com.example.emailwebsite.entity;

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
@Table(name = "email")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "emailId")
    private Integer emailId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    @NonNull
    private Integer senderId;
    @NonNull
    private Integer recipientId;

    private String subject;

    private String body;

    private String title;
    @NonNull
    private String label;
    @NonNull
    private Timestamp timeSend;
    @NonNull
    private boolean isSchedule;
    @NonNull
    private boolean isRead;

}
