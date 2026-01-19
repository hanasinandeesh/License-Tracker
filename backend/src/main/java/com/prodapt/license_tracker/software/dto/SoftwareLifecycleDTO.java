package com.prodapt.license_tracker.software.dto;

import com.prodapt.license_tracker.common.enums.DeviceStatus;
import com.prodapt.license_tracker.common.enums.SoftwareStatus;

public class SoftwareLifecycleDTO {
	
	private Integer svId;
	private String deviceId;
    private String softwareName;
    private String currentVersion;
    private String latestVersion;
    private SoftwareStatus status;
    private DeviceStatus lifecycleStage;
    
	
	public SoftwareLifecycleDTO(Integer svId,String deviceId, String softwareName, String currentVersion, String latestVersion,
			SoftwareStatus status, DeviceStatus lifecycleStage) {
		super();
		this.svId = svId;
		this.deviceId = deviceId;
		this.softwareName = softwareName;
		this.currentVersion = currentVersion;
		this.latestVersion = latestVersion;
		this.status = status;
		this.lifecycleStage = lifecycleStage;
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
	public DeviceStatus getLifecycleStage() {
		return lifecycleStage;
	}
	public void setLifecycleStage(DeviceStatus lifecycleStage) {
		this.lifecycleStage = lifecycleStage;
	}
    public Integer getSvId() {
        return svId;
    }

}
