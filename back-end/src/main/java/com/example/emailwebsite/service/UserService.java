package com.example.emailwebsite.service;

import com.example.emailwebsite.dto.UserDTO;
import com.example.emailwebsite.entity.Role;
import com.example.emailwebsite.entity.User;
import com.example.emailwebsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;


    public User getUserByUsername(String userName){
        return userRepository.findUserByUsername(userName);
    }

    public List<UserDTO> getAllUser(){
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    public void addUser(UserDTO userDTO){
        User newUser = new User();
//        if(user.getName() != null
//        && user.getUsername() != null
//        && user.getPassword() != null){
//            newUser.setName(user.getName());
//            newUser.setUserName(user.getUsername());
//            newUser.setPassword(user.getPassword());
//            newUser.setDateOfBirth(user.getDateOfBirth());
//            newUser.setPhoneNumber(user.getPhoneNumber());
//        }
//        userRepository.save(newUser);
        newUser.setName(userDTO.getFirstName() + " " + userDTO.getLastName());
        newUser.setEmailAddress(userDTO.getEmailAddress());
//        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setPassword(userDTO.getPassword());
        newUser.setRole(Role.USER);
        newUser.setPhoneNumber(userDTO.getPhoneNumber());
        userRepository.save(newUser);

    }
    public User updateUser(User user){
        return userRepository.save(user);
    }

    private UserDTO convertEntityToDto(User user){
        UserDTO userDto = new UserDTO();
        String[] name = user.getName().split(" ");
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmailAddress(user.getEmailAddress());
        userDto.setPhoneNumber(user.getPhoneNumber());
        return userDto;
    }
}
