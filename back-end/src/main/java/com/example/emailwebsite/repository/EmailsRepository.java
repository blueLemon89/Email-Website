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
    @Query(value = "select * from emails e where e.label = 1 and e.status = 'None' and e.account_id =:id", nativeQuery = true)
    List<Emails> getEmailsReceivedByUserId(Long id);
    @Query(value = "select * from emails e where e.label = 0 and e.status = 'None' and e.account_id =:id", nativeQuery = true)
    List<Emails> getEmailsSentByUserId(Long id);
    @Query(value = "select * from emails e where e.status = 'important' and e.account_id =:id", nativeQuery = true)
    List<Emails> getEmailsImportantByUserId(Long id);

    @Query(value = "select * from emails e where e.status = 'Trash' and e.account_id =:id", nativeQuery = true)
    List<Emails> getEmailsTrashByUserId(Long id);

    @Query(value = "select * from emails e where e.status =:status and e.label =:label", nativeQuery = true)
    List<Emails> getAll(String status, String label);

    @Query(value = "select * from emails e where e.sender_name like %:keyWord% or e.recipient_name like %:keyWord% " +
            "or e.subject like %:keyWord% or e.body like %:keyWord%", nativeQuery = true)
    List<Emails> getEmailsByKeyWord(String keyWord);

    @Query(value =  "select * from emails e where e.email_id =:emailId LIMIT 1", nativeQuery = true)
    Emails getEmailById(Integer emailId);
    @Query(value = "SELECT a.account_email FROM Emails e JOIN Account a ON e.account_id = a.account_id WHERE e.email_id = :emailId", nativeQuery = true)
    String getEmailAddressById(Integer emailId);
}
