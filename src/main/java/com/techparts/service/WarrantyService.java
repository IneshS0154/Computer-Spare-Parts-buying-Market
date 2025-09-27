package com.techparts.service;

import com.techparts.entity.RepairTracking;
import com.techparts.entity.User;
import com.techparts.repository.RepairTrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarrantyService {

    @Autowired
    private RepairTrackingRepository repairTrackingRepository;

    public List<RepairTracking> getWarrantyRequestsByUser(User user) {
        return repairTrackingRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public Optional<RepairTracking> getWarrantyRequestById(Long id) {
        return repairTrackingRepository.findById(id);
    }

    public RepairTracking createWarrantyRequest(User user, Long orderId, Long itemId, String issueDescription, String reason) {
        // Create warranty request logic
        RepairTracking warrantyRequest = new RepairTracking();
        warrantyRequest.setUser(user);
        warrantyRequest.setIssueDescription(issueDescription);
        warrantyRequest.setRepairRequestNumber(generateRequestNumber());
        warrantyRequest.setStatus(com.techparts.entity.RepairStatus.PENDING);
        
        return repairTrackingRepository.save(warrantyRequest);
    }

    public RepairTracking updateWarrantyRequest(RepairTracking warrantyRequest) {
        return repairTrackingRepository.save(warrantyRequest);
    }

    public void deleteWarrantyRequest(Long id) {
        repairTrackingRepository.deleteById(id);
    }

    public List<RepairTracking> getAllWarrantyRequests() {
        return repairTrackingRepository.findAll();
    }

    private String generateRequestNumber() {
        return "WR" + System.currentTimeMillis();
    }
}
