package com.prodapt.license_tracker.alert.dto;

public class DeviceExpiryAlertResponse {

	private String deviceId;
	private String licenseKey;

	public DeviceExpiryAlertResponse(String deviceId, String licenseKey) {
		this.deviceId = deviceId;
		this.licenseKey = licenseKey;
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

}
