package com.techparts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "complaint_responses")
public class ComplaintResponse {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String response;
    
    @Column(name = "is_admin_response")
    private Boolean isAdminResponse = false;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    // Constructors
    public ComplaintResponse() {}
    
    public ComplaintResponse(Complaint complaint, User user, String response, Boolean isAdminResponse) {
        this.complaint = complaint;
        this.user = user;
        this.response = response;
        this.isAdminResponse = isAdminResponse;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Complaint getComplaint() { return complaint; }
    public void setComplaint(Complaint complaint) { this.complaint = complaint; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
    
    public Boolean getIsAdminResponse() { return isAdminResponse; }
    public void setIsAdminResponse(Boolean isAdminResponse) { this.isAdminResponse = isAdminResponse; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
