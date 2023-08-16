package com.example.emailwebsite.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Integer contactId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name =  "userId", referencedColumnName = "userId")
    private User user;
}
