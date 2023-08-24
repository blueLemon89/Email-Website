package com.example.emailwebsite.repository;

import com.example.emailwebsite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value =  "SELECT * FROM user where user.user_name =:userName", nativeQuery = true)
    User findUserByUsername(String userName);
}
