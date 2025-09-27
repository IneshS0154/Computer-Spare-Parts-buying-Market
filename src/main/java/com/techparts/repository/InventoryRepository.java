package com.techparts.repository;

import com.techparts.entity.Inventory;
import com.techparts.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    
    List<Inventory> findByCategory(String category);
    
    List<Inventory> findByIsActive(Boolean isActive);
    
    List<Inventory> findByStockQuantityGreaterThan(Integer stockQuantity);
    
    List<Inventory> findByStockQuantityLessThanEqual(Integer stockQuantity);
    
    @Query("SELECT i FROM Inventory i WHERE i.name LIKE %:name% OR i.model LIKE %:name%")
    List<Inventory> findByNameOrModelContaining(@Param("name") String name);
    
    @Query("SELECT i FROM Inventory i WHERE i.category = :category AND i.isActive = true")
    List<Inventory> findActiveByCategory(@Param("category") String category);
    
    @Query("SELECT i FROM Inventory i WHERE i.stockQuantity > 0 AND i.isActive = true")
    List<Inventory> findInStockItems();
    
    @Query("SELECT i FROM Inventory i WHERE i.stockQuantity <= :threshold AND i.isActive = true")
    List<Inventory> findLowStockItems(@Param("threshold") Integer threshold);
    
    @Query("SELECT i FROM Inventory i WHERE i.stockQuantity = 0 AND i.isActive = true")
    List<Inventory> findOutOfStockItems();
    
    @Query("SELECT COUNT(i) FROM Inventory i WHERE i.isActive = true")
    long countActiveItems();
    
    @Query("SELECT COUNT(i) FROM Inventory i WHERE i.stockQuantity > 0 AND i.isActive = true")
    long countInStockItems();
    
    @Query("SELECT COUNT(i) FROM Inventory i WHERE i.stockQuantity <= :threshold AND i.isActive = true")
    long countLowStockItems(@Param("threshold") Integer threshold);
    
    List<Inventory> findBySupplier(User supplier);
}
