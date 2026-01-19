package com.prodapt.license_tracker.audit.service;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prodapt.license_tracker.audit.entity.AuditLog;
import com.prodapt.license_tracker.audit.repository.AuditLogRepository;
import com.prodapt.license_tracker.auth.security.util.LoggedInUserUtil;
import com.prodapt.license_tracker.report.util.CsvGenerator;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final LoggedInUserUtil loggedInUserUtil;

    
    public AuditLogServiceImpl(AuditLogRepository auditLogRepository,LoggedInUserUtil loggedInUserUtil) {
        this.auditLogRepository = auditLogRepository;
        this.loggedInUserUtil = loggedInUserUtil;

    }

    @Override
    public void generateAuditCsv(PrintWriter writer) {

        CsvGenerator.writeLine(
                writer,
                List.of(
                        "Action",
                        "Entity Type",
                        "Entity Id",
                        "User Id",
                        "Timestamp",
                        "Details"
                )
        );

        for (AuditLog log : auditLogRepository.findAll()) {
            CsvGenerator.writeLine(
                    writer,
                    List.of(
                            log.getAction(),
                            log.getEntityType(),
                            log.getEntityId(),
                            String.valueOf(log.getUserId()),
                            String.valueOf(log.getTimestamp()),
                            log.getDetails()
                    )
            );
        }
    }

    @Override
    public List<AuditLog> getLogsByUser(Integer userId) {
        return auditLogRepository.findByUserId(userId);
    }

    @Override
    public List<AuditLog> getLogsByEntity(String entityType) {
        return auditLogRepository.findByEntityType(entityType);
    }

    @Override
    public List<AuditLog> getLogsByDateRange(
            LocalDateTime start,
            LocalDateTime end) {
        return auditLogRepository.findByTimestampBetween(start, end);
    }

    @Override
    public void createLog(
//    		Integer userId,
            String entityType,
            String entityId,
            String action,
            String details) {
    	


        AuditLog log = new AuditLog();
        
        Integer currentUserId = loggedInUserUtil.getCurrentUserId();
        
        if (currentUserId == null) {
            throw new RuntimeException("Logged-in user not found in SecurityContext");
        }

//        log.setUserId(userId);
        log.setUserId(currentUserId);
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        log.setAction(action);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());

        auditLogRepository.save(log);
    }

}
