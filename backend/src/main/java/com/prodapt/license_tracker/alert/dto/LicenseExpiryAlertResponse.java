package com.prodapt.license_tracker.alert.dto;

import java.time.LocalDate;

public class LicenseExpiryAlertResponse {

    private String licenseKey;
    private String softwareName;
    private String vendorName;
    private int devicesUsed;
    private LocalDate validTo;
    private int remainingDays;
    private String alertLevel;

    public LicenseExpiryAlertResponse() {
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

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public int getDevicesUsed() {
        return devicesUsed;
    }

    public void setDevicesUsed(int devicesUsed) {
        this.devicesUsed = devicesUsed;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public int getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(int remainingDays) {
        this.remainingDays = remainingDays;
    }

    
	public String getAlertLevel() {
		return alertLevel;
	}

	public void setAlertLevel(String alertLevel) {
		this.alertLevel = alertLevel;
	}
    
    
}