package com.prodapt.license_tracker.license.dto;

import java.time.LocalDate;

public class LicenseExpiryAlertResponse {

    private String licenseKey;
    private String softwareName;
    private String vendorName;
    private long devicesUsed;
    private LocalDate validTo;
    private long daysRemaining;

    public LicenseExpiryAlertResponse(
            String licenseKey,
            String softwareName,
            String vendorName,
            long devicesUsed,
            LocalDate validTo,
            long daysRemaining) {

        this.licenseKey = licenseKey;
        this.softwareName = softwareName;
        this.vendorName = vendorName;
        this.devicesUsed = devicesUsed;
        this.validTo = validTo;
        this.daysRemaining = daysRemaining;
    }

    public String getLicenseKey() { return licenseKey; }
    public String getSoftwareName() { return softwareName; }
    public String getVendorName() { return vendorName; }
    public long getDevicesUsed() { return devicesUsed; }
    public LocalDate getValidTo() { return validTo; }
    public long getDaysRemaining() { return daysRemaining; }
}

