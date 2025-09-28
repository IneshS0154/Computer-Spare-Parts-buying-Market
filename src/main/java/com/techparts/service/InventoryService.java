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
    
    // Helper method to get product image path
    public String getProductImagePath(String productName) {
        if (productName == null) return "/images/products/default.jpg";
        
        // Map product names to actual image filenames
        String imageName = productName.toLowerCase();
        String imagePath = "/images/products/";
        
        // Debug logging
        System.out.println("Mapping product: " + productName + " -> " + imageName);
        
        // CPU mapping
        if (imageName.contains("high-performance") && imageName.contains("cpu")) {
            return imagePath + "high_performance_cpu.jpg";
        }
        // Graphics Card mapping
        else if (imageName.contains("gaming") && imageName.contains("graphics")) {
            return imagePath + "gaming_graphics_card.jpg"; // You may need to add this image
        }
        // RAM mapping
        else if (imageName.contains("16gb") && imageName.contains("ddr4")) {
            return imagePath + "ddr5_ram.webp"; // Using DDR5 RAM image
        }
        // SSD mapping
        else if (imageName.contains("1tb") && imageName.contains("nvme")) {
            return imagePath + "500gb_nvme_ssd.jpeg"; // Using 500GB SSD image
        }
        // Motherboard mapping
        else if (imageName.contains("gaming") && imageName.contains("motherboard")) {
            return imagePath + "gaming_motherboard.webp";
        }
        // Power Supply mapping
        else if (imageName.contains("750w") && imageName.contains("power")) {
            return imagePath + "750W_powersupply.jpg";
        }
        // CPU Cooler mapping
        else if (imageName.contains("rgb") && imageName.contains("cooler")) {
            return imagePath + "cpu_cooler.jpeg";
        }
        // Case mapping
        else if (imageName.contains("gaming") && imageName.contains("case")) {
            return imagePath + "case_fan.jpeg"; // Using case fan image
        }
        // Thermal Paste mapping
        else if (imageName.contains("thermal") && imageName.contains("paste")) {
            return imagePath + "thermal_paste.jpeg";
        }
        // Additional mappings for other products
        else if (imageName.contains("ssd")) {
            return imagePath + "500gb_nvme_ssd.jpeg";
        }
        else if (imageName.contains("cooler")) {
            return imagePath + "cpu_cooler.jpeg";
        }
        else if (imageName.contains("case")) {
            return imagePath + "case_fan.jpeg";
        }
        else if (imageName.contains("paste")) {
            return imagePath + "thermal_paste.jpeg";
        }
        else {
            // Fallback to default mapping
            String fallbackName = imageName.replace(" ", "_");
            String finalPath = imagePath + fallbackName + ".jpg";
            System.out.println("Using fallback path: " + finalPath);
            return finalPath;
        }
    }
}
