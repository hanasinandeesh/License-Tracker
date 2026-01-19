package com.prodapt.license_tracker.software.entity;

import java.time.LocalDate;

import com.prodapt.license_tracker.common.enums.SoftwareStatus;

import jakarta.persistence.*;

@Entity
@Table(name = "software_version")
public class SoftwareVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sv_id")
    private Integer svId;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(name = "software_name", nullable = false)
    private String softwareName;

    @Column(name = "current_version", nullable = false)
    private String currentVersion;

    @Column(name = "latest_version")
    private String latestVersion;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SoftwareStatus status;

    @Column(name = "last_checked")
    private LocalDate lastChecked;

    // getters & setters
    public Integer getSvId() {
        return svId;
    }

    public void setSvId(Integer svId) {
        this.svId = svId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(String latestVersion) {
        this.latestVersion = latestVersion;
    }

    public SoftwareStatus getStatus() {
        return status;
    }

    public void setStatus(SoftwareStatus status) {
        this.status = status;
    }

    public LocalDate getLastChecked() {
        return lastChecked;
    }

    public void setLastChecked(LocalDate lastChecked) {
        this.lastChecked = lastChecked;
    }
}
