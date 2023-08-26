package com.example.emailwebsite.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterDto {
    @NotEmpty(message = "Username not null")
    private String username;
    @NotEmpty(message = "Email not null")
    @Email(message = "Required email format @")
    private String email;
    @NotEmpty(message = "Password not null")
    private String password;
}
