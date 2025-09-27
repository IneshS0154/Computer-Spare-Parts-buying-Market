package com.techparts.repository;

import com.techparts.entity.RepairTracking;
import com.techparts.entity.RepairStatus;
import com.techparts.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepairTrackingRepository extends JpaRepository<RepairTracking, Long> {
    
    List<RepairTracking> findByUser(User user);
    
    List<RepairTracking> findByStatus(RepairStatus status);
    
    List<RepairTracking> findByUserAndStatus(User user, RepairStatus status);
    
    @Query("SELECT r FROM RepairTracking r WHERE r.user = :user ORDER BY r.createdAt DESC")
    List<RepairTracking> findByUserOrderByCreatedAtDesc(@Param("user") User user);
    
    @Query("SELECT r FROM RepairTracking r WHERE r.status = :status ORDER BY r.createdAt DESC")
    List<RepairTracking> findByStatusOrderByCreatedAtDesc(@Param("status") RepairStatus status);
    
    @Query("SELECT r FROM RepairTracking r WHERE r.repairRequestNumber = :requestNumber")
    Optional<RepairTracking> findByRepairRequestNumber(@Param("requestNumber") String requestNumber);
    
    @Query("SELECT COUNT(r) FROM RepairTracking r WHERE r.status = :status")
    long countByStatus(@Param("status") RepairStatus status);
    
    @Query("SELECT COUNT(r) FROM RepairTracking r WHERE r.user = :user")
    long countByUser(@Param("user") User user);
}
