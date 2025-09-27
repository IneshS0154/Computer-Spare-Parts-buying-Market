package com.techparts.controller;

import com.techparts.entity.Inventory;
import com.techparts.entity.User;
import com.techparts.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/products")
    public String productsPage(Model model) {
        List<Inventory> products = inventoryService.getAllActiveProducts();
        model.addAttribute("products", products);
        setAuthenticationStatus(model);
        return "products";
    }

    @GetMapping("/products/{id}")
    public String productDetails(@PathVariable Long id, Model model) {
        Inventory product = inventoryService.getProductById(id);
        if (product == null) {
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        setAuthenticationStatus(model);
        return "product-details";
    }

    @GetMapping("/products/category/{category}")
    public String productsByCategory(@PathVariable String category, Model model) {
        List<Inventory> products = inventoryService.getProductsByCategory(category);
        model.addAttribute("products", products);
        model.addAttribute("category", category);
        setAuthenticationStatus(model);
        return "products";
    }

    @GetMapping("/products/search")
    public String searchProducts(@RequestParam String query, Model model) {
        List<Inventory> products = inventoryService.searchProducts(query);
        model.addAttribute("products", products);
        model.addAttribute("searchQuery", query);
        setAuthenticationStatus(model);
        return "products";
    }
    
    private void setAuthenticationStatus(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            model.addAttribute("isAuthenticated", true);
            User user = (User) auth.getPrincipal();
            model.addAttribute("user", user);
        } else {
            model.addAttribute("isAuthenticated", false);
        }
    }
}
