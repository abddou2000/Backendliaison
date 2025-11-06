package com.example.login.Repositories;

import com.example.login.Models.ActivityLog;
import com.example.login.Models.ActivityLog.ActivityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long>, JpaSpecificationExecutor<ActivityLog> {

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