package com.techparts.controller;

import com.techparts.entity.User;
import com.techparts.entity.SupplierRequest;
import com.techparts.entity.Inventory;
import com.techparts.service.SupplierService;
import com.techparts.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/dashboard")
    public String supplierDashboard(Model model) {
        User currentSupplier = getCurrentUser();
        model.addAttribute("supplier", currentSupplier);
        model.addAttribute("suppliedItems", inventoryService.getItemsBySupplier(currentSupplier));
        model.addAttribute("recentRequests", supplierService.getRecentRequestsBySupplier(currentSupplier));
        return "Supplier_dashboard";
    }

    @GetMapping("/register-part")
    public String registerPart(Model model) {
        User currentSupplier = getCurrentUser();
        model.addAttribute("supplier", currentSupplier);
        return "Reg_part_supp";
    }

    @PostMapping("/register-part")
    public String registerPart(
            @RequestParam String partName,
            @RequestParam String partModel,
            @RequestParam String partCategory,
            @RequestParam String description,
            @RequestParam Double price,
            @RequestParam Integer stockQuantity,
            @RequestParam Integer warrantyPeriodMonths,
            Model model) {
        
        try {
            User currentSupplier = getCurrentUser();
            supplierService.registerNewPart(currentSupplier, partName, partModel, partCategory, 
                                          description, price, stockQuantity, warrantyPeriodMonths);
            return "redirect:/supplier/dashboard?success=true";
            
        } catch (Exception e) {
            model.addAttribute("error", "Part registration failed: " + e.getMessage());
            return "Reg_part_supp";
        }
    }

    @PostMapping("/submit-order")
    public String submitOrder(
            @RequestParam String partName,
            @RequestParam String partId,
            @RequestParam Integer quantity,
            @RequestParam String deliverTime,
            @RequestParam(required = false) String notes,
            Model model) {
        
        try {
            User currentSupplier = getCurrentUser();
            supplierService.submitOrder(currentSupplier, partName, partId, quantity, deliverTime, notes);
            return "redirect:/supplier/dashboard?order_success=true";
            
        } catch (Exception e) {
            model.addAttribute("error", "Order submission failed: " + e.getMessage());
            return "Supplier_dashboard";
        }
    }

    @GetMapping("/orders")
    public String supplierOrders(Model model) {
        User currentSupplier = getCurrentUser();
        model.addAttribute("orders", supplierService.getOrdersBySupplier(currentSupplier));
        return "supplier-orders";
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }
}
