package com.prodapt.license_tracker.device.service;

import java.util.List;

import com.prodapt.license_tracker.device.dto.DeviceLifecycleView;
import com.prodapt.license_tracker.device.dto.NonCompliantDeviceResponse;
import com.prodapt.license_tracker.device.entity.Device;

public interface DeviceService {

    Device addDevice(Device device);

    List<Device> getAllDevices();

    Device getDeviceById(String deviceId);

    Device updateDevice(String deviceId, Device device);

    List<NonCompliantDeviceResponse> getNonCompliantDevices();

    void decommissionDevice(String deviceId);

    void deleteDevice(String deviceId);

    boolean existsByDeviceId(String deviceId);
    
    List<DeviceLifecycleView> getDeviceLifecycle(String deviceId);
}
