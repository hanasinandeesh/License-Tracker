package com.prodapt.license_tracker.report.dto;

import java.time.LocalDateTime;

public class AuditLogReportRow {

	private String action;
    private String entityType;
    private String entityId;
    private String userEmail;
    private LocalDateTime timestamp;

    public AuditLogReportRow(
            String action,
            String entityType,
            String entityId,
            String userEmail,
            LocalDateTime timestamp) {
        this.action = action;
        this.entityType = entityType;
        this.entityId = entityId;
        this.userEmail = userEmail;
        this.timestamp = timestamp;
    }

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
    
}
