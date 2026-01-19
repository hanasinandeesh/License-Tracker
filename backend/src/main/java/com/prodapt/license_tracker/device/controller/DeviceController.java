package com.prodapt.license_tracker.device.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.prodapt.license_tracker.device.dto.NonCompliantDeviceResponse;
import com.prodapt.license_tracker.device.dto.DeviceLifecycleView;
import com.prodapt.license_tracker.device.entity.Device;
import com.prodapt.license_tracker.common.enums.DeviceStatus;
import com.prodapt.license_tracker.device.service.DeviceService;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Device> addDevice(@RequestBody Device device) {
        return new ResponseEntity<>(
                deviceService.addDevice(device),
                HttpStatus.CREATED
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','ENGINEER','AUDITOR')")
    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    @PreAuthorize("hasAnyRole('ADMIN','ENGINEER','AUDITOR')")
    @GetMapping("/{deviceId}")
    public ResponseEntity<Device> getDeviceById(@PathVariable String deviceId) {
        return ResponseEntity.ok(deviceService.getDeviceById(deviceId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','ENGINEER')")
    @PutMapping("/{deviceId}")
    public ResponseEntity<Device> updateDevice(
            @PathVariable String deviceId,
            @RequestBody Device device) {

        return ResponseEntity.ok(
                deviceService.updateDevice(deviceId, device)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{deviceId}/decommission")
    public ResponseEntity<String> decommissionDevice(
            @PathVariable String deviceId) {

        deviceService.decommissionDevice(deviceId);
        return ResponseEntity.ok("Device decommissioned successfully");
    }

    @PreAuthorize("hasAnyRole('ADMIN','AUDITOR')")
    @GetMapping("/non-compliant")
    public ResponseEntity<List<NonCompliantDeviceResponse>> getNonCompliantDevices() {
        return ResponseEntity.ok(deviceService.getNonCompliantDevices());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{deviceId}")
    public ResponseEntity<Void> deleteDevice(@PathVariable String deviceId) {
        deviceService.deleteDevice(deviceId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','ENGINEER','AUDITOR')")
    @GetMapping("/{deviceId}/lifecycle")
    public ResponseEntity<List<DeviceLifecycleView>> viewLifecycle(
            @PathVariable String deviceId) {

        return ResponseEntity.ok(
                deviceService.getDeviceLifecycle(deviceId)
        );
    }
}
