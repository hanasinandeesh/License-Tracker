package com.prodapt.license_tracker.device.dto;

public class NonCompliantDeviceResponse {

    private String deviceId;
    private String reason;

    public NonCompliantDeviceResponse(String deviceId, String reason) {
        this.deviceId = deviceId;
        this.reason = reason;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getReason() {
        return reason;
    }
}
