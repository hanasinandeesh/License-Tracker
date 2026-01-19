package com.prodapt.license_tracker.device.dto;

import com.prodapt.license_tracker.common.enums.DeviceStatus;

public class DeviceLifecycleView {
	
	private String deviceId;
    private String softwareName;
    private DeviceStatus status;
    private String note;
	public DeviceLifecycleView(String deviceId, String softwareName, DeviceStatus status, String note) {
		super();
		this.deviceId = deviceId;
		this.softwareName = softwareName;
		this.status = status;
		this.note = note;
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
	public DeviceStatus getStatus() {
		return status;
	}
	public void setStatus(DeviceStatus status) {
		this.status = status;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
    

}
