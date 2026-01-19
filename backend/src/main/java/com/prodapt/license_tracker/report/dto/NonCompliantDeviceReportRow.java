package com.prodapt.license_tracker.report.dto;

import java.time.LocalDate;

public class NonCompliantDeviceReportRow {

    private String deviceId;
    private String deviceName;
    private String licenseKey;
    private String softwareName;
    private LocalDate licenseExpiryDate;
    private String reason;

    public NonCompliantDeviceReportRow(
            String deviceId,
            String deviceName,
            String licenseKey,
            String softwareName,
            LocalDate licenseExpiryDate,
            String reason) {

        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.licenseKey = licenseKey;
        this.softwareName = softwareName;
        this.licenseExpiryDate = licenseExpiryDate;
        this.reason = reason;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getLicenseKey() {
        return licenseKey;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public LocalDate getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public String getReason() {
        return reason;
    }
}
