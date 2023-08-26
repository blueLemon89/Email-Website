package com.example.emailwebsite.repository;

import com.example.emailwebsite.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Integer> {
    Roles findByRolesName(String name);
}

