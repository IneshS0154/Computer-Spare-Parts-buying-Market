package com.techparts.service;

import com.techparts.entity.Inventory;
import com.techparts.entity.User;
import com.techparts.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> getAllActiveProducts() {
        return inventoryRepository.findInStockItems();
    }

    public List<Inventory> getProductsByCategory(String category) {
        return inventoryRepository.findActiveByCategory(category);
    }

    public List<Inventory> searchProducts(String query) {
        return inventoryRepository.findByNameOrModelContaining(query);
    }

    public Inventory getProductById(Long id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    public List<Inventory> getItemsBySupplier(User supplier) {
        return inventoryRepository.findBySupplier(supplier);
    }

    public Inventory createProduct(Inventory product) {
        return inventoryRepository.save(product);
    }

    public Inventory updateProduct(Inventory product) {
        return inventoryRepository.save(product);
    }

    public void deleteProduct(Long id) {
        inventoryRepository.deleteById(id);
    }

    public List<Inventory> getAllProducts() {
        return inventoryRepository.findAll();
    }

    public List<Inventory> getLowStockItems() {
        return inventoryRepository.findLowStockItems(10);
    }

    public List<Inventory> getOutOfStockItems() {
        return inventoryRepository.findOutOfStockItems();
    }

    public long getTotalProducts() {
        return inventoryRepository.count();
    }

    public long getInStockProducts() {
        return inventoryRepository.countInStockItems();
    }

    // Filtering methods
    public List<Inventory> getFilteredProducts(String category, String priceRange, String availability, String sortBy) {
        // Get all active products first
        List<Inventory> products = inventoryRepository.findAll().stream()
                .filter(product -> product.getIsActive())
                .collect(java.util.stream.Collectors.toList());
        
        // Apply category filter
        if (category != null && !category.isEmpty() && !category.equals("all")) {
            products = products.stream()
                    .filter(product -> product.getCategory().equals(category))
                    .collect(java.util.stream.Collectors.toList());
        }
        
        // Apply price range filter
        if (priceRange != null && !priceRange.isEmpty() && !priceRange.equals("all")) {
            switch (priceRange) {
                case "under-100":
                    products = products.stream()
                            .filter(product -> product.getPrice().doubleValue() < 100)
                            .collect(java.util.stream.Collectors.toList());
                    break;
                case "100-500":
                    products = products.stream()
                            .filter(product -> product.getPrice().doubleValue() >= 100 && product.getPrice().doubleValue() <= 500)
                            .collect(java.util.stream.Collectors.toList());
                    break;
                case "500-1000":
                    products = products.stream()
                            .filter(product -> product.getPrice().doubleValue() > 500 && product.getPrice().doubleValue() <= 1000)
                            .collect(java.util.stream.Collectors.toList());
                    break;
                case "over-1000":
                    products = products.stream()
                            .filter(product -> product.getPrice().doubleValue() > 1000)
                            .collect(java.util.stream.Collectors.toList());
                    break;
            }
        }
        
        // Apply availability filter
        if (availability != null && !availability.isEmpty() && !availability.equals("all")) {
            switch (availability) {
                case "in-stock":
                    products = products.stream()
                            .filter(product -> product.getStockQuantity() > 0)
                            .collect(java.util.stream.Collectors.toList());
                    break;
                case "out-of-stock":
                    products = products.stream()
                            .filter(product -> product.getStockQuantity() == 0)
                            .collect(java.util.stream.Collectors.toList());
                    break;
            }
        }
        
        // Apply sorting
        if (sortBy != null && !sortBy.isEmpty() && !sortBy.equals("default")) {
            switch (sortBy) {
                case "price-asc":
                    products = products.stream()
                            .sorted((p1, p2) -> p1.getPrice().compareTo(p2.getPrice()))
                            .collect(java.util.stream.Collectors.toList());
                    break;
                case "price-desc":
                    products = products.stream()
                            .sorted((p1, p2) -> p2.getPrice().compareTo(p1.getPrice()))
                            .collect(java.util.stream.Collectors.toList());
                    break;
                case "name-asc":
                    products = products.stream()
                            .sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))
                            .collect(java.util.stream.Collectors.toList());
                    break;
                case "name-desc":
                    products = products.stream()
                            .sorted((p1, p2) -> p2.getName().compareTo(p1.getName()))
                            .collect(java.util.stream.Collectors.toList());
                    break;
            }
        }
        
        return products;
    }
    

    public List<String> getAllCategories() {
        return inventoryRepository.findDistinctCategories();
    }
}
