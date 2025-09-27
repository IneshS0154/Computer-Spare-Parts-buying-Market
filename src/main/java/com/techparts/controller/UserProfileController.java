package com.techparts.controller;

import com.techparts.entity.User;
import com.techparts.service.UserService;
import com.techparts.service.OrderService;
import com.techparts.service.WarrantyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private WarrantyService warrantyService;

    @GetMapping("/user/profile")
    public String userProfile(@RequestParam(required = false) Boolean success, Model model) {
        User currentUser = getCurrentUser();
        model.addAttribute("user", currentUser);
        if (success != null && success) {
            model.addAttribute("success", true);
        }
        return "user_profile";
    }

    @GetMapping("/user/edit-profile")
    public String editProfile(Model model) {
        User currentUser = getCurrentUser();
        model.addAttribute("user", currentUser);
        return "edit-user";
    }

    @PostMapping("/user/update-profile")
    public String updateProfile(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String address,
            Model model) {
        
        try {
            User currentUser = getCurrentUser();
            currentUser.setFirstName(firstName);
            currentUser.setLastName(lastName);
            currentUser.setEmail(email);
            currentUser.setPhone(phone);
            currentUser.setAddress(address);
            
            userService.updateUser(currentUser);
            return "redirect:/user/profile?success=true";
            
        } catch (Exception e) {
            model.addAttribute("error", "Update failed: " + e.getMessage());
            return "edit-user";
        }
    }

    @GetMapping("/user/orders")
    public String userOrders(Model model) {
        User currentUser = getCurrentUser();
        model.addAttribute("orders", orderService.getOrdersByUser(currentUser));
        return "user-orders";
    }

    @GetMapping("/user/orders/{orderId}")
    public String userOrderDetails(@PathVariable Long orderId, Model model) {
        User currentUser = getCurrentUser();
        model.addAttribute("order", orderService.getOrderById(orderId));
        model.addAttribute("orderItems", orderService.getOrderItemsByOrderId(orderId));
        return "user-order-part";
    }

    @GetMapping("/user/warranty-form/{orderId}/{itemId}")
    public String warrantyForm(@PathVariable Long orderId, @PathVariable Long itemId, Model model) {
        User currentUser = getCurrentUser();
        model.addAttribute("order", orderService.getOrderById(orderId));
        model.addAttribute("item", orderService.getOrderItemById(itemId));
        return "user-warranty-form";
    }

    @PostMapping("/user/submit-warranty")
    public String submitWarranty(
            @RequestParam Long orderId,
            @RequestParam Long itemId,
            @RequestParam String issueDescription,
            @RequestParam String reason,
            Model model) {
        
        try {
            User currentUser = getCurrentUser();
            warrantyService.createWarrantyRequest(currentUser, orderId, itemId, issueDescription, reason);
            return "redirect:/user/orders?success=true";
            
        } catch (Exception e) {
            model.addAttribute("error", "Warranty request failed: " + e.getMessage());
            return "user-warranty-form";
        }
    }

    @GetMapping("/user/refunds")
    public String userRefunds(Model model) {
        User currentUser = getCurrentUser();
        model.addAttribute("refunds", warrantyService.getWarrantyRequestsByUser(currentUser));
        return "User-refund";
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }
}
