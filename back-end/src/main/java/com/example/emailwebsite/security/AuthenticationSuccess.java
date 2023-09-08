package com.example.emailwebsite.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.io.IOException;
import java.util.Collection;

@Component
@Configuration
public class AuthenticationSuccess implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String userName = authentication.getName();
        RedirectAttributes attributes = new RedirectAttributesModelMap();
        attributes.addAttribute("emailAddress", userName);
        response.sendRedirect("/user/index?emailAddress=" + userName);
    }

    private Boolean isAdmin(Authentication authentication){
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities){
            if (authority.getAuthority().equals("ROLE_ADMIN")) return true;
        }
        return false;
    }

    private Boolean isUser(Authentication authentication){
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities){
            if (authority.getAuthority().equals("ROLE_USER")) return true;
        }
        return false;
    }
}