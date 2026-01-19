package com.prodapt.license_tracker.report.dto;

import java.time.LocalDate;

//package: com.prodapt.license_tracker.report.dto
public class LicenseReportDTO {

	private String licenseKey;
	private String deviceId;
	private String softwareName;
	private LocalDate expiryDate;

	public LicenseReportDTO(String licenseKey, String deviceId, String softwareName, LocalDate expiryDate) {

		this.licenseKey = licenseKey;
		this.deviceId = deviceId;
		this.softwareName = softwareName;
		this.expiryDate = expiryDate;
	}

	public String getLicenseKey() {
		return licenseKey;
	}

	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
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

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

}
