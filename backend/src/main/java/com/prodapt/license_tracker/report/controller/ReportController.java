package com.prodapt.license_tracker.report.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prodapt.license_tracker.audit.entity.AuditLog;
import com.prodapt.license_tracker.audit.service.AuditLogService;
import com.prodapt.license_tracker.report.service.AuditReportService;
import com.prodapt.license_tracker.report.service.DeviceReportService;
import com.prodapt.license_tracker.report.service.LicenseReportService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final LicenseReportService licenseReportService;
    private final DeviceReportService deviceReportService;
    private final AuditReportService auditReportService;
    private final AuditLogService auditLogService;

    public ReportController(
            LicenseReportService licenseReportService,
            DeviceReportService deviceReportService,
            AuditReportService auditReportService,
            AuditLogService auditLogService) {

        this.licenseReportService = licenseReportService;
        this.deviceReportService = deviceReportService;
        this.auditReportService = auditReportService;
        this.auditLogService = auditLogService;
    }

    // ================= LICENSE CSV =================
    @PreAuthorize("hasAnyRole('ADMIN','AUDITOR','ENGINEER')")
    @GetMapping("/licenses/csv")
    public void licenseUsageCsv(HttpServletResponse response) throws IOException {

        setCsvHeaders(response, "license-usage-report.csv");

        PrintWriter writer = response.getWriter();
        licenseReportService.generateLicenseUsageCsv(writer);
        writer.flush();
    }

    // ================= NON-COMPLIANT DEVICES CSV =================
    @PreAuthorize("hasAnyRole('ADMIN','AUDITOR')")
    @GetMapping("/devices/non-compliant/csv")
    public void nonCompliantDevicesCsv(HttpServletResponse response) throws IOException {

        setCsvHeaders(response, "non-compliant-devices.csv");

        deviceReportService.generateNonCompliantDevicesCsv(response.getWriter());
    }

    // ================= AUDIT REPORT CSV =================
    @PreAuthorize("hasAnyRole('ADMIN','AUDITOR')")
    @GetMapping("/audit/csv")
    public void auditCsv(HttpServletResponse response) throws IOException {

        setCsvHeaders(response, "audit-report.csv");

        auditReportService.generateAuditCsv(response.getWriter());
    }

    // ================= AUDIT DATA (JSON) =================
    @PreAuthorize("hasAnyRole('ADMIN','AUDITOR')")
    @GetMapping("/audit/entity/{entityType}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByEntity(
            @PathVariable String entityType) {

        return ResponseEntity.ok(
                auditLogService.getLogsByEntity(entityType)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','AUDITOR')")
    @GetMapping("/audit/user/{userId}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByUser(
            @PathVariable Integer userId) {

        return ResponseEntity.ok(
                auditLogService.getLogsByUser(userId)
        );
    }

    // ================= COMMON CSV HEADERS =================
    private void setCsvHeaders(HttpServletResponse response, String fileName) {

        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");

        // 🔴 IMPORTANT FIX: Disable caching
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=" + fileName
        );
    }
}
