package com.prodapt.license_tracker.assignment.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(
    name = "assignment",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"device_id", "license_key"})
    }
)
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Integer assignmentId;

    
    @Column(name = "device_id", length = 30)
    private String deviceId;


    @Column(name = "license_key", length = 50,nullable = false)
    private String licenseKey;

    @Column(name = "assigned_on")
    private LocalDate assignedOn;


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

    public LocalDate getAssignedOn() {
        return assignedOn;
    }

    public void setAssignedOn(LocalDate assignedOn) {
        this.assignedOn = assignedOn;
    }
}
