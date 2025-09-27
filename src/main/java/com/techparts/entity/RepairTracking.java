package com.techparts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "repair_tracking")
public class RepairTracking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warranty_id", nullable = false)
    private UserBoughtPartWarranty warranty;
    
    @NotBlank
    @Size(max = 50)
    @Column(name = "repair_request_number", unique = true)
    private String repairRequestNumber;
    
    @NotBlank
    @Column(name = "issue_description", columnDefinition = "TEXT")
    private String issueDescription;
    
    @Enumerated(EnumType.STRING)
    private RepairStatus status = RepairStatus.PENDING;
    
    @Column(name = "admin_notes", columnDefinition = "TEXT")
    private String adminNotes;
    
    @Column(name = "repair_start_date")
    private LocalDateTime repairStartDate;
    
    @Column(name = "repair_completion_date")
    private LocalDateTime repairCompletionDate;
    
    @DecimalMin(value = "0.0")
    @Column(name = "repair_cost", precision = 10, scale = 2)
    private BigDecimal repairCost = BigDecimal.ZERO;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    // Relationships
    @OneToMany(mappedBy = "repairTracking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RepairLog> repairLogs;
    
    // Constructors
    public RepairTracking() {}
    
    public RepairTracking(User user, UserBoughtPartWarranty warranty, String repairRequestNumber, String issueDescription) {
        this.user = user;
        this.warranty = warranty;
        this.repairRequestNumber = repairRequestNumber;
        this.issueDescription = issueDescription;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public UserBoughtPartWarranty getWarranty() { return warranty; }
    public void setWarranty(UserBoughtPartWarranty warranty) { this.warranty = warranty; }
    
    public String getRepairRequestNumber() { return repairRequestNumber; }
    public void setRepairRequestNumber(String repairRequestNumber) { this.repairRequestNumber = repairRequestNumber; }
    
    public String getIssueDescription() { return issueDescription; }
    public void setIssueDescription(String issueDescription) { this.issueDescription = issueDescription; }
    
    public RepairStatus getStatus() { return status; }
    public void setStatus(RepairStatus status) { this.status = status; }
    
    public String getAdminNotes() { return adminNotes; }
    public void setAdminNotes(String adminNotes) { this.adminNotes = adminNotes; }
    
    public LocalDateTime getRepairStartDate() { return repairStartDate; }
    public void setRepairStartDate(LocalDateTime repairStartDate) { this.repairStartDate = repairStartDate; }
    
    public LocalDateTime getRepairCompletionDate() { return repairCompletionDate; }
    public void setRepairCompletionDate(LocalDateTime repairCompletionDate) { this.repairCompletionDate = repairCompletionDate; }
    
    public BigDecimal getRepairCost() { return repairCost; }
    public void setRepairCost(BigDecimal repairCost) { this.repairCost = repairCost; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public Set<RepairLog> getRepairLogs() { return repairLogs; }
    public void setRepairLogs(Set<RepairLog> repairLogs) { this.repairLogs = repairLogs; }
    
    // Helper methods
    public boolean isCompleted() {
        return status == RepairStatus.COMPLETED;
    }
    
    public boolean isPending() {
        return status == RepairStatus.PENDING;
    }
    
    public boolean isInProgress() {
        return status == RepairStatus.IN_PROGRESS;
    }
    
    public long getRepairDurationDays() {
        if (repairStartDate != null && repairCompletionDate != null) {
            return java.time.Duration.between(repairStartDate, repairCompletionDate).toDays();
        }
        return 0;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
