package com.example.emailwebsite.service;

import com.example.emailwebsite.entity.User;
import com.example.emailwebsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User getUserByUsername(String userName){
        return userRepository.findUserByUsername(userName);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public void addUser(User user){
        User newUser = new User();
        if(user.getName() != null
        && user.getUserName() != null
        && user.getPassword() != null){
            newUser.setName(user.getName());
            newUser.setUserName(user.getUserName());
            newUser.setPassword(user.getPassword());
            newUser.setDateOfBirth(user.getDateOfBirth());
            newUser.setPhoneNumber(user.getPhoneNumber());
        }
        userRepository.save(newUser);
    }
    public User updateUser(User user){
        return userRepository.save(user);
    }
}
