package com.example.login.Repositories;

import com.example.login.Models.ActivityLog;
import com.example.login.Models.ActivityLog.ActivityStatus;
import com.example.login.Models.ActivityLog.ActivityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

    // Recherche combinée avec filtres
    @Query("SELECT a FROM ActivityLog a WHERE " +
            "(:search IS NULL OR LOWER(a.userName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(a.action) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(a.target) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:type IS NULL OR a.type = :type) " +
            "AND (:status IS NULL OR a.status = :status) " +
            "AND (:startDate IS NULL OR a.timestamp >= :startDate)")
    Page<ActivityLog> findWithFilters(
            @Param("search") String search,
            @Param("type") ActivityType type,
            @Param("status") ActivityStatus status,
            @Param("startDate") LocalDateTime startDate,
            Pageable pageable
    );

    // Statistiques
    @Query("SELECT COUNT(a) FROM ActivityLog a WHERE a.timestamp >= :startDate")
    long countByTimestampAfter(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT COUNT(a) FROM ActivityLog a WHERE a.status = :status")
    long countByStatus(@Param("status") ActivityStatus status);

    @Query("SELECT COUNT(DISTINCT a.userId) FROM ActivityLog a")
    long countDistinctUsers();

    // Activités récentes
    List<ActivityLog> findTop50ByOrderByTimestampDesc();
}