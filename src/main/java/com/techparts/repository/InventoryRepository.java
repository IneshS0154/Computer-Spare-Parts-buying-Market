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
    
    // Filtering methods
    @Query("SELECT DISTINCT i.category FROM Inventory i WHERE i.isActive = true ORDER BY i.category")
    List<String> findDistinctCategories();
    
    @Query("SELECT i FROM Inventory i WHERE " +
           "i.isActive = true " +
           "AND (:category IS NULL OR i.category = :category) " +
           "AND (:priceRange IS NULL OR " +
           "    (:priceRange = 'under-100' AND i.price < 100) OR " +
           "    (:priceRange = '100-500' AND i.price >= 100 AND i.price <= 500) OR " +
           "    (:priceRange = '500-1000' AND i.price > 500 AND i.price <= 1000) OR " +
           "    (:priceRange = 'over-1000' AND i.price > 1000)) " +
           "AND (:availability IS NULL OR " +
           "    (:availability = 'in-stock' AND i.stockQuantity > 0) OR " +
           "    (:availability = 'out-of-stock' AND i.stockQuantity = 0)) " +
           "ORDER BY " +
           "CASE WHEN :sortBy = 'price-asc' THEN i.price END ASC, " +
           "CASE WHEN :sortBy = 'price-desc' THEN i.price END DESC, " +
           "CASE WHEN :sortBy = 'name-asc' THEN i.name END ASC, " +
           "CASE WHEN :sortBy = 'name-desc' THEN i.name END DESC, " +
           "i.createdAt DESC")
    List<Inventory> findFilteredProducts(@Param("category") String category, 
                                       @Param("priceRange") String priceRange, 
                                       @Param("availability") String availability, 
                                       @Param("sortBy") String sortBy);
    
    // Alternative simpler methods for debugging
    List<Inventory> findByCategoryAndIsActive(String category, Boolean isActive);
    List<Inventory> findByStockQuantityAndIsActive(Integer stockQuantity, Boolean isActive);
    List<Inventory> findByIsActiveOrderByPriceAsc(Boolean isActive);
    List<Inventory> findByIsActiveOrderByPriceDesc(Boolean isActive);
    List<Inventory> findByIsActiveOrderByNameAsc(Boolean isActive);
    List<Inventory> findByIsActiveOrderByNameDesc(Boolean isActive);
    
    // Simple test methods
    @Query("SELECT i FROM Inventory i WHERE i.isActive = true AND i.stockQuantity = 0")
    List<Inventory> findOutOfStockActiveProducts();
    
    @Query("SELECT i FROM Inventory i WHERE i.isActive = true AND i.stockQuantity > 0")
    List<Inventory> findInStockActiveProducts();
}
