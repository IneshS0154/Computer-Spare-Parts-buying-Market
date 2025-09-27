package com.techparts.controller;

import com.techparts.entity.User;
import com.techparts.entity.SupplierRequest;
import com.techparts.service.AdminService;
import com.techparts.service.UserService;
import com.techparts.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("totalUsers", userService.getTotalUsers());
        model.addAttribute("totalOrders", adminService.getTotalOrders());
        model.addAttribute("totalProducts", adminService.getTotalProducts());
        model.addAttribute("totalSuppliers", adminService.getTotalSuppliers());
        model.addAttribute("recentOrders", adminService.getRecentOrders());
        model.addAttribute("lowStockItems", adminService.getLowStockItems());
        return "admin-dashboard";
    }

    @GetMapping("/suppliers")
    public String adminSuppliers(Model model) {
        List<User> suppliers = userService.getUsersByRole(com.techparts.entity.UserRole.SUPPLIER);
        model.addAttribute("suppliers", suppliers);
        return "admin-supplier";
    }

    @PostMapping("/suppliers/register")
    public String registerSupplier(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String phone,
            @RequestParam String address,
            Model model) {
        
        try {
            User supplier = new User();
            supplier.setUsername(username);
            supplier.setEmail(email);
            supplier.setPassword(password);
            supplier.setFirstName(firstName);
            supplier.setLastName(lastName);
            supplier.setPhone(phone);
            supplier.setAddress(address);
            supplier.setRole(com.techparts.entity.UserRole.SUPPLIER);
            
            userService.createUser(supplier);
            return "redirect:/admin/suppliers?success=true";
            
        } catch (Exception e) {
            model.addAttribute("error", "Supplier registration failed: " + e.getMessage());
            return "admin-supplier";
        }
    }

    @GetMapping("/supplier-orders")
    public String adminSupplierOrders(Model model) {
        List<SupplierRequest> supplierOrders = supplierService.getAllSupplierRequests();
        model.addAttribute("supplierOrders", supplierOrders);
        return "admin_supplier_order";
    }

    @PostMapping("/supplier-orders/{id}/approve")
    public String approveSupplierOrder(@PathVariable Long id, @RequestParam Integer approvedQuantity) {
        supplierService.approveSupplierRequest(id, approvedQuantity);
        return "redirect:/admin/supplier-orders?approved=true";
    }

    @PostMapping("/supplier-orders/{id}/reject")
    public String rejectSupplierOrder(@PathVariable Long id) {
        supplierService.rejectSupplierRequest(id);
        return "redirect:/admin/supplier-orders?rejected=true";
    }

    @GetMapping("/users")
    public String adminUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin-users";
    }

    @GetMapping("/orders")
    public String adminOrders(Model model) {
        model.addAttribute("orders", adminService.getAllOrders());
        return "admin-orders";
    }

    @GetMapping("/inventory")
    public String adminInventory(Model model) {
        model.addAttribute("products", adminService.getAllProducts());
        return "admin-inventory";
    }
}
