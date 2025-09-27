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
}
