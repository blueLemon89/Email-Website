package com.example.emailwebsite.repository;

import com.example.emailwebsite.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    Account findByEmailAddress(String emailAddress);
}

