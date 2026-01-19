package com.prodapt.license_tracker.audit.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prodapt.license_tracker.audit.entity.AuditLog;
import com.prodapt.license_tracker.report.dto.AuditLogReportRow;

public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {

    List<AuditLog> findByEntityType(String entityType);

    List<AuditLog> findByEntityId(String entityId);

    List<AuditLog> findByUserId(Integer userId);
        
	@Query("""
			SELECT new com.prodapt.license_tracker.report.dto.AuditLogReportRow(
			    a.action,
			    a.entityType,
			    a.entityId,
			    u.email,
			    a.timestamp
			)
			FROM AuditLog a
			JOIN User u ON a.userId = u.userId
			ORDER BY a.timestamp DESC
			""")
	List<AuditLogReportRow> fetchAuditLogReport();
    

	@Query("""
			    SELECT a FROM AuditLog a
			    WHERE a.timestamp BETWEEN :start AND :end
			    ORDER BY a.timestamp DESC
			""")
	List<AuditLog> findByDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
	
	
	 List<AuditLog> findByTimestampBetween(LocalDateTime start,LocalDateTime end);
}
