package com.prodapt.license_tracker.assignment.dto;

import java.time.LocalDate;

public class AssignmentViewDTO {

    private Integer assignmentId;
    private String deviceId;
    private String licenseKey;
    private String softwareName;
    private LocalDate assignedOn;

    public AssignmentViewDTO(
            Integer assignmentId,
            String deviceId,
            String licenseKey,
            String softwareName,
            LocalDate assignedOn) {

        this.assignmentId = assignmentId;
        this.deviceId = deviceId;
        this.licenseKey = licenseKey;
        this.softwareName = softwareName;
        this.assignedOn = assignedOn;
    }

    public Integer getAssignmentId() { return assignmentId; }
    public String getDeviceId() { return deviceId; }
    public String getLicenseKey() { return licenseKey; }
    public String getSoftwareName() { return softwareName; }
    public LocalDate getAssignedOn() { return assignedOn; }
}
