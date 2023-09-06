package com.example.emailwebsite.repository;

import com.example.emailwebsite.entity.Emails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailsRepository extends JpaRepository<Emails, Integer> {
    @Query("")
    List<Emails> getAllEmailByUserName();
}
