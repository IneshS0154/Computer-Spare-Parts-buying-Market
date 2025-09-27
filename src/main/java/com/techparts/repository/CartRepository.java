package com.techparts.repository;

import com.techparts.entity.Cart;
import com.techparts.entity.Inventory;
import com.techparts.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
    List<Cart> findByUser(User user);
    
    Optional<Cart> findByUserAndInventory(User user, Inventory inventory);
    
    @Query("SELECT c FROM Cart c WHERE c.user = :user ORDER BY c.createdAt DESC")
    List<Cart> findByUserOrderByCreatedAtDesc(@Param("user") User user);
    
    @Query("SELECT COUNT(c) FROM Cart c WHERE c.user = :user")
    long countByUser(@Param("user") User user);
    
    @Query("SELECT SUM(c.quantity * i.price) FROM Cart c JOIN c.inventory i WHERE c.user = :user")
    Double getTotalPriceByUser(@Param("user") User user);
    
    void deleteByUser(User user);
    
    void deleteByUserAndInventory(User user, Inventory inventory);
}
