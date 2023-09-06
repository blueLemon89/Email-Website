package com.example.emailwebsite.service;

import com.example.emailwebsite.dto.RegisterDto;
import com.example.emailwebsite.entity.Account;
import com.example.emailwebsite.entity.Roles;
import com.example.emailwebsite.repository.AccountRepository;
import com.example.emailwebsite.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final AccountRepository accountRepo;
    private final RolesRepository rolesRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(AccountRepository accountRepo, RolesRepository rolesRepo, PasswordEncoder passwordEncoder) {
        this.accountRepo = accountRepo;
        this.rolesRepo = rolesRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepo.findByEmailAddress(username);
        if (account == null) {
            throw new UsernameNotFoundException("Invalid email or password");
        }
        List<GrantedAuthority> authorities = getAuthorities(account.getRoles());

        return new User(account.getEmailAddress(), account.getPassword(), authorities);
    }
    private List<GrantedAuthority> getAuthorities(List<Roles> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Roles role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRolesName()));
        }
        return authorities;
    }

    public Account save(RegisterDto registerDto) {
        Roles roles = rolesRepo.findByRolesName("ROLE_USER");
        if (roles == null) {
            checkUserRoleExist();
        }
        var account = Account.builder()
                .username(registerDto.getUsername())
                .emailAddress(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .roles(Collections.singletonList(roles))
                .build();
        return accountRepo.save(account);
    }
    private void checkUserRoleExist(){
        Roles roles = new Roles();
        roles.setRolesName("ROLE_USER");
        rolesRepo.save(roles);
    }
    public Account findByEmail(String email) {
        return accountRepo.findByEmailAddress(email);
    }

    public List<Account> findAllUser() {
        return accountRepo.findAll();
    }

    public Long getAccountIdFromEmailAddress(String emailAddress){
        Account account = accountRepo.findByEmailAddress(emailAddress);
        Long id = account.getAccount_id();
        return id;
    }


}
