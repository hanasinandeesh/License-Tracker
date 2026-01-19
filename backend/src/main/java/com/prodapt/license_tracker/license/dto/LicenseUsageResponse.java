package com.prodapt.license_tracker.license.dto;

public class LicenseUsageResponse {

    private String licenseKey;
    private Integer maxUsage;
    private long used;
    private long remaining;

    public LicenseUsageResponse(String licenseKey, Integer maxUsage, long used) {
        this.licenseKey = licenseKey;
        this.maxUsage = maxUsage;
        this.used = used;
        this.remaining = (maxUsage != null) ? maxUsage - used : 0;
    }

    public String getLicenseKey() {
        return licenseKey;
    }

    public Integer getMaxUsage() {
        return maxUsage;
    }

    public long getUsed() {
        return used;
    }

    public long getRemaining() {
        return remaining;
    }
}
