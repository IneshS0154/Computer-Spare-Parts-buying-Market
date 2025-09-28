package com.techparts.controller;

import com.techparts.entity.Cart;
import com.techparts.entity.Inventory;
import com.techparts.entity.User;
import com.techparts.service.CartService;
import com.techparts.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public String viewCart(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return "redirect:/login";
        }

        User user = (User) auth.getPrincipal();
        List<Cart> cartItems = cartService.getCartItems(user);
        BigDecimal cartTotal = cartService.getCartTotal(user);
        long cartItemCount = cartService.getCartItemCount(user);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotal", cartTotal);
        model.addAttribute("cartItemCount", cartItemCount);
        model.addAttribute("isAuthenticated", true);
        model.addAttribute("user", user);
        model.addAttribute("inventoryService", inventoryService); // Add service for image paths

        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam(defaultValue = "1") Integer quantity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return "redirect:/login";
        }

        User user = (User) auth.getPrincipal();
        Inventory inventory = inventoryService.getProductById(productId);
        
        if (inventory != null) {
            cartService.addToCart(user, inventory, quantity);
        }

        return "redirect:/products";
    }

    @PostMapping("/update")
    public String updateCartItem(@RequestParam Long cartId, @RequestParam Integer quantity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return "redirect:/login";
        }

        if (quantity <= 0) {
            cartService.removeFromCart(cartId);
        } else {
            cartService.updateCartItemQuantity(cartId, quantity);
        }

        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long cartId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return "redirect:/login";
        }

        cartService.removeFromCart(cartId);
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return "redirect:/login";
        }

        User user = (User) auth.getPrincipal();
        cartService.clearCart(user);
        return "redirect:/cart";
    }

    @GetMapping("/count")
    @ResponseBody
    public long getCartCount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return 0;
        }

        User user = (User) auth.getPrincipal();
        return cartService.getCartItemCount(user);
    }
}
