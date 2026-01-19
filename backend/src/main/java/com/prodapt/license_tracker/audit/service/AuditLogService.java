package com.prodapt.license_tracker.audit.service;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

import com.prodapt.license_tracker.audit.entity.AuditLog;

public interface AuditLogService {
	
	List<AuditLog> getLogsByUser(Integer userId);

    List<AuditLog> getLogsByEntity(String entityType);
    
    
    List<AuditLog> getLogsByDateRange(
            LocalDateTime start,
            LocalDateTime end
    );
    
    void createLog(
//            Integer userId,
            String entityType,
            String entityId,
            String action,
            String details
    );

    void generateAuditCsv(PrintWriter writer);
        
}
