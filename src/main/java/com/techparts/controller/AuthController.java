package com.techparts.controller;

import com.techparts.entity.User;
import com.techparts.entity.UserRole;
import com.techparts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String address,
            @RequestParam(defaultValue = "CUSTOMER") String userType,
            Model model) {
        
        try {
            System.out.println("Registration attempt for: " + username + ", email: " + email + ", userType: " + userType);
            
            UserRole role = userType.equals("supplier") ? UserRole.SUPPLIER : UserRole.CUSTOMER;
            
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhone(phone);
            user.setAddress(address);
            user.setRole(role);
            
            System.out.println("About to create user: " + user.getUsername());
            User savedUser = userService.createUser(user);
            System.out.println("User created successfully with ID: " + savedUser.getId());
            
            return "redirect:/login?success=true";
            
        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "register";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        if (user.getRole() == UserRole.ADMIN) {
            return "redirect:/admin/dashboard";
        } else if (user.getRole() == UserRole.SUPPLIER) {
            return "redirect:/supplier/dashboard";
        } else {
            return "redirect:/user/dashboard";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
    
    @GetMapping("/check-auth")
    public String checkAuth(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            model.addAttribute("isAuthenticated", true);
            User user = (User) auth.getPrincipal();
            model.addAttribute("user", user);
        } else {
            model.addAttribute("isAuthenticated", false);
        }
        return "fragments/auth-status :: authStatus";
    }
}
