package com.techparts.service;

import com.techparts.entity.Order;
import com.techparts.entity.Inventory;
import com.techparts.entity.User;
import com.techparts.entity.Complaint;
import com.techparts.repository.OrderRepository;
import com.techparts.repository.InventoryRepository;
import com.techparts.repository.UserRepository;
import com.techparts.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryRepository inventoryRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ComplaintRepository complaintRepository;

    public long getTotalOrders() {
        return orderRepository.count();
    }

    public long getTotalProducts() {
        return inventoryRepository.count();
    }

    public long getTotalSuppliers() {
        return inventoryRepository.count();
    }
    
    public long getTotalUsers() {
        return userRepository.count();
    }
    
    public long getTotalInventory() {
        return inventoryRepository.count();
    }
    
    public long getPendingComplaints() {
        return complaintRepository.count();
    }

    public List<Order> getRecentOrders() {
        return orderRepository.findByOrderStatusOrderByCreatedAtDesc(com.techparts.entity.OrderStatus.PENDING);
    }
    
    public List<Inventory> getLowStockItems() {
        return inventoryRepository.findLowStockItems(10);
    }
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public List<Inventory> getAllProducts() {
        return inventoryRepository.findAll();
    }
}
