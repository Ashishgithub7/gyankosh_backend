package com.example.gyankosh.service;

import com.example.gyankosh.Entity.UserUserDetail;
import com.example.gyankosh.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Searching for user with email: " + email);
        return userRepo.findByEmail(email)
                .map(UserUserDetail::new)
                .orElseThrow(() -> {
                    System.out.println("User not found for email: " + email);
                    return new UsernameNotFoundException("No user found");
                });
    }
}

