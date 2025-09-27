package com.techparts.service;

import com.techparts.entity.User;
import com.techparts.entity.Admin;
import com.techparts.repository.UserRepository;
import com.techparts.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // First try to find in Admin table
        Admin admin = adminRepository.findByUsername(username).orElse(null);
        if (admin != null) {
            return admin;
        }
        
        // If not found in Admin table, try User table
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return user;
    }
}
