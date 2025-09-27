package com.techparts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "supplier_requests")
public class SupplierRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private User supplier;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;
    
    @NotBlank
    @Size(max = 100)
    @Column(name = "part_name")
    private String partName;
    
    @Size(max = 100)
    @Column(name = "part_model")
    private String partModel;
    
    @NotBlank
    @Size(max = 50)
    @Column(name = "part_category")
    private String partCategory;
    
    @NotNull
    @Min(1)
    @Column(name = "quantity_offered")
    private Integer quantityOffered;
    
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;
    
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;
    
    @Enumerated(EnumType.STRING)
    private SupplierRequestStatus status = SupplierRequestStatus.PENDING;
    
    @Min(0)
    @Column(name = "approved_quantity")
    private Integer approvedQuantity = 0;
    
    @Column(name = "admin_notes", columnDefinition = "TEXT")
    private String adminNotes;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    // Constructors
    public SupplierRequest() {}
    
    public SupplierRequest(User supplier, String partName, String partCategory, 
                          Integer quantityOffered, BigDecimal unitPrice) {
        this.supplier = supplier;
        this.partName = partName;
        this.partCategory = partCategory;
        this.quantityOffered = quantityOffered;
        this.unitPrice = unitPrice;
        this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantityOffered));
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getSupplier() { return supplier; }
    public void setSupplier(User supplier) { this.supplier = supplier; }
    
    public Inventory getInventory() { return inventory; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }
    
    public String getPartName() { return partName; }
    public void setPartName(String partName) { this.partName = partName; }
    
    public String getPartModel() { return partModel; }
    public void setPartModel(String partModel) { this.partModel = partModel; }
    
    public String getPartCategory() { return partCategory; }
    public void setPartCategory(String partCategory) { this.partCategory = partCategory; }
    
    public Integer getQuantityOffered() { return quantityOffered; }
    public void setQuantityOffered(Integer quantityOffered) { 
        this.quantityOffered = quantityOffered;
        this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantityOffered));
    }
    
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { 
        this.unitPrice = unitPrice;
        this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantityOffered));
    }
    
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    
    public SupplierRequestStatus getStatus() { return status; }
    public void setStatus(SupplierRequestStatus status) { this.status = status; }
    
    public Integer getApprovedQuantity() { return approvedQuantity; }
    public void setApprovedQuantity(Integer approvedQuantity) { this.approvedQuantity = approvedQuantity; }
    
    public String getAdminNotes() { return adminNotes; }
    public void setAdminNotes(String adminNotes) { this.adminNotes = adminNotes; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    // Helper methods
    public boolean isPending() {
        return status == SupplierRequestStatus.PENDING;
    }
    
    public boolean isApproved() {
        return status == SupplierRequestStatus.APPROVED;
    }
    
    public boolean isRejected() {
        return status == SupplierRequestStatus.REJECTED;
    }
    
    public boolean isPartiallyApproved() {
        return status == SupplierRequestStatus.PARTIALLY_APPROVED;
    }
    
    public BigDecimal getApprovedTotalPrice() {
        return unitPrice.multiply(BigDecimal.valueOf(approvedQuantity));
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
