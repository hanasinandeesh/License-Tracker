package com.prodapt.license_tracker.device.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.prodapt.license_tracker.common.enums.DeviceStatus;

import jakarta.persistence.*;

@Entity
@Table(name = "device")
public class Device {

    @Id
    @Column(name = "device_id", length = 30)
    private String deviceId;

    @Column(name = "type", length = 30)
    private String type;

    @Column(name = "ip_address", length = 15, unique = true)
    private String ipAddress;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "model", length = 50)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DeviceStatus status;
    
    @Column(name = "note", length = 500)
    private String note;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "decommissioned_on")
    private LocalDate decommissionedOn;

    @Column(name = "decommission_reason")
    private String decommissionReason;

    //  Getters & Setters 

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

	public LocalDate getDecommissionedOn() {
		return decommissionedOn;
	}

	public void setDecommissionedOn(LocalDate decommissionedOn) {
		this.decommissionedOn = decommissionedOn;
	}

	public String getDecommissionReason() {
		return decommissionReason;
	}

	public void setDecommissionReason(String decommissionReason) {
		this.decommissionReason = decommissionReason;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
    
	
  
}