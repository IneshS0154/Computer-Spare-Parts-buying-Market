package com.techparts.repository;

import com.techparts.entity.Complaint;
import com.techparts.entity.ComplaintStatus;
import com.techparts.entity.ComplaintType;
import com.techparts.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    
    List<Complaint> findByUser(User user);
    
    List<Complaint> findByStatus(ComplaintStatus status);
    
    List<Complaint> findByComplaintType(ComplaintType complaintType);
    
    List<Complaint> findByUserAndStatus(User user, ComplaintStatus status);
    
    @Query("SELECT c FROM Complaint c WHERE c.user = :user ORDER BY c.createdAt DESC")
    List<Complaint> findByUserOrderByCreatedAtDesc(@Param("user") User user);
    
    @Query("SELECT c FROM Complaint c WHERE c.status = :status ORDER BY c.createdAt DESC")
    List<Complaint> findByStatusOrderByCreatedAtDesc(@Param("status") ComplaintStatus status);
    
    @Query("SELECT c FROM Complaint c WHERE c.title LIKE %:keyword% OR c.description LIKE %:keyword%")
    List<Complaint> findByTitleOrDescriptionContaining(@Param("keyword") String keyword);
    
    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = :status")
    long countByStatus(@Param("status") ComplaintStatus status);
    
    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.complaintType = :type")
    long countByComplaintType(@Param("type") ComplaintType type);
    
    @Query("SELECT AVG(c.rating) FROM Complaint c WHERE c.rating IS NOT NULL")
    Double getAverageRating();
}
