package com.techparts.service;

import com.techparts.entity.User;
import com.techparts.entity.SupplierRequest;
import com.techparts.entity.Inventory;
import com.techparts.repository.SupplierRequestRepository;
import com.techparts.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SupplierService {

    @Autowired
    private SupplierRequestRepository supplierRequestRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryService inventoryService;

    public List<SupplierRequest> getRecentRequestsBySupplier(User supplier) {
        return supplierRequestRepository.findBySupplierOrderByCreatedAtDesc(supplier);
    }

    public List<SupplierRequest> getOrdersBySupplier(User supplier) {
        return supplierRequestRepository.findBySupplier(supplier);
    }

    public List<SupplierRequest> getAllSupplierRequests() {
        return supplierRequestRepository.findAll();
    }

    public SupplierRequest submitOrder(User supplier, String partName, String partId, 
                                     Integer quantity, String deliverTime, String notes) {
        SupplierRequest request = new SupplierRequest();
        request.setSupplier(supplier);
        request.setPartName(partName);
        request.setPartModel(partId);
        request.setPartCategory("General");
        request.setQuantityOffered(quantity);
        request.setUnitPrice(BigDecimal.ZERO); // Will be set by admin
        request.setTotalPrice(BigDecimal.ZERO);
        request.setStatus(com.techparts.entity.SupplierRequestStatus.PENDING);
        
        return supplierRequestRepository.save(request);
    }

    public Inventory registerNewPart(User supplier, String partName, String partModel, 
                                   String partCategory, String description, Double price, 
                                   Integer stockQuantity, Integer warrantyPeriodMonths) {
        Inventory part = new Inventory();
        part.setName(partName);
        part.setModel(partModel);
        part.setCategory(partCategory);
        part.setDescription(description);
        part.setPrice(BigDecimal.valueOf(price));
        part.setStockQuantity(stockQuantity);
        part.setWarrantyPeriodMonths(warrantyPeriodMonths);
        part.setSupplier(supplier);
        part.setIsActive(true);
        
        return inventoryRepository.save(part);
    }

    public void approveSupplierRequest(Long requestId, Integer approvedQuantity) {
        SupplierRequest request = supplierRequestRepository.findById(requestId).orElse(null);
        if (request != null) {
            request.setStatus(com.techparts.entity.SupplierRequestStatus.APPROVED);
            request.setApprovedQuantity(approvedQuantity);
            supplierRequestRepository.save(request);
            
            // Update inventory if part exists
            if (request.getInventory() != null) {
                request.getInventory().addStock(approvedQuantity);
                inventoryRepository.save(request.getInventory());
            }
        }
    }

    public void rejectSupplierRequest(Long requestId) {
        SupplierRequest request = supplierRequestRepository.findById(requestId).orElse(null);
        if (request != null) {
            request.setStatus(com.techparts.entity.SupplierRequestStatus.REJECTED);
            supplierRequestRepository.save(request);
        }
    }
}
