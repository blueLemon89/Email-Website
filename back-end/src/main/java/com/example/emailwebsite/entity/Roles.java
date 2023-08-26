package com.example.emailwebsite.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roles_id;
    @Column(name = "roles_name")
    private String rolesName;

    @ManyToMany(mappedBy = "roles")
    private List<Account> accountEntities = new ArrayList<>();
}
