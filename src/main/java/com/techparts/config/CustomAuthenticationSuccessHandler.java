package com.techparts.config;

import com.techparts.entity.User;
import com.techparts.entity.Admin;
import com.techparts.entity.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
        
        Object principal = authentication.getPrincipal();
        String redirectUrl = "/products"; // Default for customers
        
        if (principal instanceof Admin) {
            // Admin user - redirect to admin dashboard
            redirectUrl = "/admin/dashboard";
        } else if (principal instanceof User) {
            // Regular user - check role
            User user = (User) principal;
            UserRole role = user.getRole();
            
            switch (role) {
                case SUPPLIER:
                    redirectUrl = "/supplier/dashboard";
                    break;
                case CUSTOMER:
                default:
                    redirectUrl = "/products";
                    break;
            }
        }
        
        response.sendRedirect(redirectUrl);
    }
}
