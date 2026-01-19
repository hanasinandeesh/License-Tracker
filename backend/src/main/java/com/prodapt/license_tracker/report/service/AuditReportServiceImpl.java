package com.prodapt.license_tracker.report.service;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prodapt.license_tracker.audit.entity.AuditLog;
import com.prodapt.license_tracker.audit.repository.AuditLogRepository;
import com.prodapt.license_tracker.report.util.CsvGenerator;

@Service
public class AuditReportServiceImpl implements AuditReportService {

    private final AuditLogRepository auditLogRepository;

    public AuditReportServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public void generateAuditCsv(PrintWriter writer) {

        CsvGenerator.writeLine(writer,
                List.of(
                        "Action",
                        "Entity Type",
                        "Entity Id",
                        "User Id",
                        "Timestamp",
                        "Details"
                ));

        auditLogRepository.findAll().forEach(log ->
                CsvGenerator.writeLine(writer,
                        List.of(
                                log.getAction(),
                                log.getEntityType(),
                                log.getEntityId(),
                                String.valueOf(log.getUserId()),
                                String.valueOf(log.getTimestamp()),
                                log.getDetails()
                        ))
        );
    }
}
