package com.prodapt.license_tracker.report.dto;

import java.time.LocalDate;

public class LicenseUsageReportRow {

    private String licenseKey;
    private String softwareName;
    private String version;
    private Integer totalAssignedDevices;
    private Long activeAssignments;
    private Long expiredAssignments;
    private LocalDate validTo;

    public LicenseUsageReportRow(
            String licenseKey,
            String softwareName,
            String version,
            Integer totalAssignedDevices,
            Long activeAssignments,
            Long expiredAssignments,
            LocalDate validTo) {

        this.licenseKey = licenseKey;
        this.softwareName = softwareName;
        this.version = version;
        this.totalAssignedDevices = totalAssignedDevices;
        this.activeAssignments = activeAssignments;
        this.expiredAssignments = expiredAssignments;
        this.validTo = validTo;
    }

    public String getLicenseKey() {
        return licenseKey;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public String getVersion() {
        return version;
    }

    public Integer getTotalAssignedDevices() {
        return totalAssignedDevices;
    }

    public Long getActiveAssignments() {
        return activeAssignments;
    }

    public Long getExpiredAssignments() {
        return expiredAssignments;
    }

    public LocalDate getValidTo() {
        return validTo;
    }
}
