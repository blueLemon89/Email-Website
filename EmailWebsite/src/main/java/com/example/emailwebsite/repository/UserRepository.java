package com.example.emailwebsite.repository;

import com.example.emailwebsite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value =  "SELECT * FROM user where user.user_name =:userName", nativeQuery = true)
    public User findUserByUsername(String userName);
}
