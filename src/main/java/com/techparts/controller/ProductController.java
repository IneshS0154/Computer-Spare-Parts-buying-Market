package com.techparts.controller;

import com.techparts.entity.Inventory;
import com.techparts.entity.User;
import com.techparts.service.InventoryService;
import com.techparts.service.CartService;
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

    @Autowired
    private CartService cartService;

    @GetMapping("/products")
    public String productsPage(@RequestParam(required = false) String category,
                              @RequestParam(required = false) String priceRange,
                              @RequestParam(required = false) String availability,
                              @RequestParam(required = false) String sortBy,
                              Model model) {
        // Debug logging
        System.out.println("Filter parameters - Category: " + category + ", PriceRange: " + priceRange + 
                          ", Availability: " + availability + ", SortBy: " + sortBy);
        
        List<Inventory> products;
        if (category != null || priceRange != null || availability != null || sortBy != null) {
            products = inventoryService.getFilteredProducts(category, priceRange, availability, sortBy);
            System.out.println("Filtered products count: " + products.size());
        } else {
            // Show all active products (including out of stock) by default
            products = inventoryService.getAllProducts().stream()
                    .filter(product -> product.getIsActive())
                    .collect(java.util.stream.Collectors.toList());
            System.out.println("All products count: " + products.size());
        }
        
        model.addAttribute("products", products);
        model.addAttribute("categories", inventoryService.getAllCategories());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedPriceRange", priceRange);
        model.addAttribute("selectedAvailability", availability);
        model.addAttribute("selectedSortBy", sortBy);
        model.addAttribute("inventoryService", inventoryService); // Add service to model for image path
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
        model.addAttribute("inventoryService", inventoryService); // Add service to model for image path
        setAuthenticationStatus(model);
        return "product-page";
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
        model.addAttribute("categories", inventoryService.getAllCategories());
        model.addAttribute("inventoryService", inventoryService); // Add service for image paths
        setAuthenticationStatus(model);
        return "products";
    }
    
    private void setAuthenticationStatus(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            model.addAttribute("isAuthenticated", true);
            User user = (User) auth.getPrincipal();
            model.addAttribute("user", user);
            long cartItemCount = cartService.getCartItemCount(user);
            model.addAttribute("cartItemCount", cartItemCount);
        } else {
            model.addAttribute("isAuthenticated", false);
            model.addAttribute("cartItemCount", 0);
        }
    }
}
