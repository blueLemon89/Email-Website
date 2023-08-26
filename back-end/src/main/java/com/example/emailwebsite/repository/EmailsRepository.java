package com.example.emailwebsite.repository;

import com.example.emailwebsite.entity.Emails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailsRepository extends JpaRepository<Emails, Integer> {
}
