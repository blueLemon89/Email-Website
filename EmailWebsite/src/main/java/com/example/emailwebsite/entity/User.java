package com.example.emailwebsite.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(generator = "userId")
    @GenericGenerator(name = "userId", strategy = "increment")
    private Integer userId;
    @NonNull
    private String name;
    @NonNull
    @Column(name = "user_name")
    private String userName;
    @NonNull
    private String password;
    @NonNull
    private Date dateOfBirth;
    @NonNull
    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Email> emails;
}
