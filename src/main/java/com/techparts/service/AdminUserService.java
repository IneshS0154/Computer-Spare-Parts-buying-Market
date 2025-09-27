package com.techparts.service;

import com.techparts.entity.Admin;
import com.techparts.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminUserService {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Admin createAdmin(Admin admin) {
        // Check if username already exists
        if (adminRepository.existsByUsername(admin.getUsername())) {
            throw new RuntimeException("Admin username already exists");
        }
        
        // Check if email already exists
        if (adminRepository.existsByEmail(admin.getEmail())) {
            throw new RuntimeException("Admin email already exists");
        }
        
        // Encode password
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        
        return adminRepository.save(admin);
    }
    
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username).orElse(null);
    }
    
    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email).orElse(null);
    }
    
    public boolean existsByUsername(String username) {
        return adminRepository.existsByUsername(username);
    }
    
    public boolean existsByEmail(String email) {
        return adminRepository.existsByEmail(email);
    }
}
