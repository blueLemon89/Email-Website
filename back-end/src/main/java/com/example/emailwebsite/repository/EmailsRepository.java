package com.example.emailwebsite.repository;

import com.example.emailwebsite.entity.Emails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailsRepository extends JpaRepository<Emails, Integer> {
    @Query(value = "select * from emails e where e.account_id =:id", nativeQuery = true)
    List<Emails> getAllEmailByUserId(Long id);
    @Query(value = "select * from emails e where e.label = 1 and e.account_id =:id", nativeQuery = true)
    List<Emails> getEmailsReceivedByUserId(Long id);
    @Query(value = "select * from emails e where e.label = 0 and e.account_id =:id", nativeQuery = true)
    List<Emails> getEmailsSentByUserId(Long id);
    @Query(value = "select * from emails e where e.label = 3 and e.account_id =:id", nativeQuery = true)
    List<Emails> getEmailsImportantByUserId(Long id);

    @Query(value = "select * from emails e where e.label = 2 and e.account_id =:id", nativeQuery = true)
    List<Emails> getEmailsTrashByUserId(Long id);
}
