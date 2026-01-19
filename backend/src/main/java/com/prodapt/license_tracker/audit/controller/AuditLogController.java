package com.prodapt.license_tracker.audit.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.prodapt.license_tracker.audit.entity.AuditLog;
import com.prodapt.license_tracker.audit.service.AuditLogService;
import com.prodapt.license_tracker.report.service.AuditReportService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

	private final AuditLogService auditLogService;
	private final AuditReportService auditReportService;

	public AuditLogController(AuditLogService auditLogService, AuditReportService auditReportService) {

		this.auditLogService = auditLogService;
		this.auditReportService = auditReportService;
	}

	@PreAuthorize("hasAnyRole('ADMIN','AUDITOR')")
	@GetMapping("/entity/{type}")
	public ResponseEntity<List<AuditLog>> getByEntity(@PathVariable String type) {
		return ResponseEntity.ok(auditLogService.getLogsByEntity(type));
	}

	@PreAuthorize("hasAnyRole('ADMIN','AUDITOR')")
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<AuditLog>> getByUser(@PathVariable Integer userId) {
		return ResponseEntity.ok(auditLogService.getLogsByUser(userId));
	}

	@PreAuthorize("hasAnyRole('ADMIN','AUDITOR')")
	@GetMapping("/date-range")
	public ResponseEntity<List<AuditLog>> getByDateRange(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,

			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

		return ResponseEntity.ok(auditLogService.getLogsByDateRange(start, end));
	}

	@PreAuthorize("hasAnyRole('ADMIN','AUDITOR')")
	@GetMapping("/csv")
	public void downloadAuditLogCsv(HttpServletResponse response) throws IOException {

		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=audit-logs.csv");

		auditReportService.generateAuditCsv(response.getWriter());
	}
}
