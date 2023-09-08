package com.example.emailwebsite.repository;

import com.example.emailwebsite.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    Account findByEmailAddress(String emailAddress);

    @Query(value = "SELECT account_id FROM account a where a.account_email =:emailAddress", nativeQuery = true)
    Long getIdByEmailAddress(String emailAddress);
    @Query(value = "SELECT account_email FROM account a where a.account_name =:recipientName", nativeQuery = true)
    String findEmailAddressByUserName(String recipientName);
}

