package com.techparts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "user_bought_part_warranty")
public class UserBoughtPartWarranty {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;
    
    @NotNull
    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;
    
    @NotNull
    @Column(name = "warranty_start_date")
    private LocalDateTime warrantyStartDate;
    
    @NotNull
    @Column(name = "warranty_end_date")
    private LocalDateTime warrantyEndDate;
    
    @Column(name = "is_warranty_valid")
    private Boolean isWarrantyValid = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    // Relationships
    @OneToMany(mappedBy = "warranty", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RepairTracking> repairRequests;
    
    // Constructors
    public UserBoughtPartWarranty() {}
    
    public UserBoughtPartWarranty(User user, Order order, Inventory inventory, 
                                 LocalDateTime purchaseDate, LocalDateTime warrantyStartDate, 
                                 LocalDateTime warrantyEndDate) {
        this.user = user;
        this.order = order;
        this.inventory = inventory;
        this.purchaseDate = purchaseDate;
        this.warrantyStartDate = warrantyStartDate;
        this.warrantyEndDate = warrantyEndDate;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    
    public Inventory getInventory() { return inventory; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }
    
    public LocalDateTime getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDateTime purchaseDate) { this.purchaseDate = purchaseDate; }
    
    public LocalDateTime getWarrantyStartDate() { return warrantyStartDate; }
    public void setWarrantyStartDate(LocalDateTime warrantyStartDate) { this.warrantyStartDate = warrantyStartDate; }
    
    public LocalDateTime getWarrantyEndDate() { return warrantyEndDate; }
    public void setWarrantyEndDate(LocalDateTime warrantyEndDate) { this.warrantyEndDate = warrantyEndDate; }
    
    public Boolean getIsWarrantyValid() { return isWarrantyValid; }
    public void setIsWarrantyValid(Boolean isWarrantyValid) { this.isWarrantyValid = isWarrantyValid; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public Set<RepairTracking> getRepairRequests() { return repairRequests; }
    public void setRepairRequests(Set<RepairTracking> repairRequests) { this.repairRequests = repairRequests; }
    
    // Helper methods
    public boolean isWarrantyActive() {
        LocalDateTime now = LocalDateTime.now();
        return isWarrantyValid && now.isAfter(warrantyStartDate) && now.isBefore(warrantyEndDate);
    }
    
    public boolean isWarrantyExpired() {
        return LocalDateTime.now().isAfter(warrantyEndDate);
    }
    
    public long getDaysUntilExpiry() {
        return java.time.Duration.between(LocalDateTime.now(), warrantyEndDate).toDays();
    }
}
