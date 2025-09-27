package com.techparts.repository;

import com.techparts.entity.SupplierRequest;
import com.techparts.entity.SupplierRequestStatus;
import com.techparts.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRequestRepository extends JpaRepository<SupplierRequest, Long> {
    
    List<SupplierRequest> findBySupplier(User supplier);
    
    List<SupplierRequest> findByStatus(SupplierRequestStatus status);
    
    List<SupplierRequest> findBySupplierAndStatus(User supplier, SupplierRequestStatus status);
    
    @Query("SELECT s FROM SupplierRequest s WHERE s.supplier = :supplier ORDER BY s.createdAt DESC")
    List<SupplierRequest> findBySupplierOrderByCreatedAtDesc(@Param("supplier") User supplier);
    
    @Query("SELECT s FROM SupplierRequest s WHERE s.status = :status ORDER BY s.createdAt DESC")
    List<SupplierRequest> findByStatusOrderByCreatedAtDesc(@Param("status") SupplierRequestStatus status);
    
    @Query("SELECT s FROM SupplierRequest s WHERE s.partName LIKE %:name% OR s.partModel LIKE %:name%")
    List<SupplierRequest> findByPartNameOrModelContaining(@Param("name") String name);
    
    @Query("SELECT COUNT(s) FROM SupplierRequest s WHERE s.status = :status")
    long countByStatus(@Param("status") SupplierRequestStatus status);
    
    @Query("SELECT COUNT(s) FROM SupplierRequest s WHERE s.supplier = :supplier")
    long countBySupplier(@Param("supplier") User supplier);
}
