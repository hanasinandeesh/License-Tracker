package com.prodapt.license_tracker.audit.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "audit_log")
public class AuditLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer logId;

	@Column(name = "user_id", nullable = false)
	private Integer userId;

	private String entityType;

	private String entityId;

	private String action;

	private LocalDateTime timestamp;

	@Column(columnDefinition = "TEXT")
	private String details;

	@PrePersist
	public void onCreate() {
		this.timestamp = LocalDateTime.now();
	}

	public AuditLog() {
		super();
	}

	public AuditLog(Integer logId, Integer userId, String entityType, String entityId, String action,
			LocalDateTime timestamp, String details) {
		super();
		this.logId = logId;
		this.userId = userId;
		this.entityType = entityType;
		this.entityId = entityId;
		this.action = action;
		this.timestamp = timestamp;
		this.details = details;
	}

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
