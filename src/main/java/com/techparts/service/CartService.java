package com.techparts.service;

import com.techparts.entity.Cart;
import com.techparts.entity.Inventory;
import com.techparts.entity.User;
import com.techparts.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart addToCart(User user, Inventory inventory, Integer quantity) {
        Optional<Cart> existingCart = cartRepository.findByUserAndInventory(user, inventory);
        
        if (existingCart.isPresent()) {
            Cart cart = existingCart.get();
            cart.setQuantity(cart.getQuantity() + quantity);
            return cartRepository.save(cart);
        } else {
            Cart cart = new Cart(user, inventory, quantity);
            return cartRepository.save(cart);
        }
    }

    public List<Cart> getCartItems(User user) {
        return cartRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public long getCartItemCount(User user) {
        return cartRepository.countByUser(user);
    }

    public BigDecimal getCartTotal(User user) {
        Double total = cartRepository.getTotalPriceByUser(user);
        return total != null ? BigDecimal.valueOf(total) : BigDecimal.ZERO;
    }

    public void updateCartItemQuantity(Long cartId, Integer quantity) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (cart.isPresent()) {
            Cart cartItem = cart.get();
            cartItem.setQuantity(quantity);
            cartRepository.save(cartItem);
        }
    }

    public void removeFromCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    public void clearCart(User user) {
        cartRepository.deleteByUser(user);
    }

    public void removeItemFromCart(User user, Inventory inventory) {
        cartRepository.deleteByUserAndInventory(user, inventory);
    }
}
