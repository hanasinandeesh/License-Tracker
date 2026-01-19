package com.prodapt.license_tracker.assignment.dto;

import java.time.LocalDate;

public class AssignmentViewResponse {
	
	private Integer assignmentId;
    private String deviceId;
    private String licenseKey;
    private String softwareName;
    private LocalDate assignedOn;
    
	public AssignmentViewResponse(Integer assignmentId, String deviceId, String licenseKey, String softwareName,
			LocalDate assignedOn) {
		super();
		this.assignmentId = assignmentId;
		this.deviceId = deviceId;
		this.licenseKey = licenseKey;
		this.softwareName = softwareName;
		this.assignedOn = assignedOn;
	}
	public Integer getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(Integer assignmentId) {
		this.assignmentId = assignmentId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getLicenseKey() {
		return licenseKey;
	}
	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}
	public String getSoftwareName() {
		return softwareName;
	}
	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}
	public LocalDate getAssignedOn() {
		return assignedOn;
	}
	public void setAssignedOn(LocalDate assignedOn) {
		this.assignedOn = assignedOn;
	}
    

}
