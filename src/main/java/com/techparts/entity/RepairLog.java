package com.techparts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "repair_logs")
public class RepairLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repair_tracking_id", nullable = false)
    private RepairTracking repairTracking;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String logMessage;
    
    @Enumerated(EnumType.STRING)
    private RepairLogType logType;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    // Constructors
    public RepairLog() {}
    
    public RepairLog(RepairTracking repairTracking, User user, String logMessage, RepairLogType logType) {
        this.repairTracking = repairTracking;
        this.user = user;
        this.logMessage = logMessage;
        this.logType = logType;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public RepairTracking getRepairTracking() { return repairTracking; }
    public void setRepairTracking(RepairTracking repairTracking) { this.repairTracking = repairTracking; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public String getLogMessage() { return logMessage; }
    public void setLogMessage(String logMessage) { this.logMessage = logMessage; }
    
    public RepairLogType getLogType() { return logType; }
    public void setLogType(RepairLogType logType) { this.logType = logType; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
