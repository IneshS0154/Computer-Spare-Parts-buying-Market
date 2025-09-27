package com.techparts.repository;

import com.techparts.entity.User;
import com.techparts.entity.UserBoughtPartWarranty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserBoughtPartWarrantyRepository extends JpaRepository<UserBoughtPartWarranty, Long> {
    
    List<UserBoughtPartWarranty> findByUser(User user);
    
    @Query("SELECT w FROM UserBoughtPartWarranty w WHERE w.user = :user ORDER BY w.purchaseDate DESC")
    List<UserBoughtPartWarranty> findByUserOrderByPurchaseDateDesc(@Param("user") User user);
    
    @Query("SELECT w FROM UserBoughtPartWarranty w WHERE w.isWarrantyValid = true AND w.warrantyEndDate > :currentDate")
    List<UserBoughtPartWarranty> findActiveWarranties(@Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT w FROM UserBoughtPartWarranty w WHERE w.user = :user AND w.isWarrantyValid = true AND w.warrantyEndDate > :currentDate")
    List<UserBoughtPartWarranty> findActiveWarrantiesByUser(@Param("user") User user, @Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT w FROM UserBoughtPartWarranty w WHERE w.warrantyEndDate BETWEEN :startDate AND :endDate")
    List<UserBoughtPartWarranty> findWarrantiesExpiringBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(w) FROM UserBoughtPartWarranty w WHERE w.user = :user")
    long countByUser(@Param("user") User user);
    
    @Query("SELECT COUNT(w) FROM UserBoughtPartWarranty w WHERE w.isWarrantyValid = true AND w.warrantyEndDate > :currentDate")
    long countActiveWarranties(@Param("currentDate") LocalDateTime currentDate);
}
