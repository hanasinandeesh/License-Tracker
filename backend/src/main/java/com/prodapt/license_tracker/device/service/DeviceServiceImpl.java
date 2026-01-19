package com.prodapt.license_tracker.device.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prodapt.license_tracker.assignment.entity.Assignment;
import com.prodapt.license_tracker.assignment.repository.AssignmentRepository;
import com.prodapt.license_tracker.audit.service.AuditLogService;
import com.prodapt.license_tracker.common.enums.DeviceStatus;
import com.prodapt.license_tracker.device.dto.DeviceLifecycleView;
import com.prodapt.license_tracker.device.dto.NonCompliantDeviceResponse;
import com.prodapt.license_tracker.device.entity.Device;
import com.prodapt.license_tracker.device.exception.DeviceNotFoundException;
import com.prodapt.license_tracker.device.repository.DeviceRepository;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

	private final DeviceRepository deviceRepository;
	private final AssignmentRepository assignmentRepository;
	private final AuditLogService auditLogService;

	public DeviceServiceImpl(DeviceRepository deviceRepository, AssignmentRepository assignmentRepository,
			AuditLogService auditLogService) {

		this.deviceRepository = deviceRepository;
		this.assignmentRepository = assignmentRepository;
		this.auditLogService = auditLogService;
	}

	@Override
	public Device addDevice(Device device) {

		if (deviceRepository.existsById(device.getDeviceId())) {
			throw new RuntimeException("Device ID already exists");
		}

		if (deviceRepository.existsByIpAddress(device.getIpAddress())) {
			throw new RuntimeException("IP Address already exists");
		}

		Device saved = deviceRepository.save(device);

		auditLogService.createLog(
				"DEVICE", 
				saved.getDeviceId(),
				"CREATE", 
				"Device created");

		return saved;
	}

	@Override
	public List<Device> getAllDevices() {
		return deviceRepository.findAll();
	}

	@Override
	public Device getDeviceById(String deviceId) {
		return deviceRepository.findById(deviceId)
				.orElseThrow(() -> new DeviceNotFoundException("Device not found: " + deviceId));
	}

	@Override
	public Device updateDevice(String deviceId, Device updatedDevice) {

		Device existingDevice = deviceRepository.findById(deviceId)
				.orElseThrow(() -> new DeviceNotFoundException("Device not found: " + deviceId));

		existingDevice.setType(updatedDevice.getType());
		existingDevice.setIpAddress(updatedDevice.getIpAddress());
		existingDevice.setLocation(updatedDevice.getLocation());
		existingDevice.setModel(updatedDevice.getModel());
		existingDevice.setStatus(updatedDevice.getStatus());

		Device saved = deviceRepository.save(existingDevice);

		auditLogService.createLog(
				"DEVICE",
				deviceId, 
				"UPDATE", 
				"Device updated");

		return saved;
	}

	@Override
	public List<NonCompliantDeviceResponse> getNonCompliantDevices() {

		List<NonCompliantDeviceResponse> result = new ArrayList<>();

		List<Device> devicesWithoutLicense = deviceRepository.findDevicesWithoutLicense();

		for (Device device : devicesWithoutLicense) {
			result.add(new NonCompliantDeviceResponse(device.getDeviceId(), "NO_LICENSE_ASSIGNED"));
		}

		List<Assignment> expiredAssignments = assignmentRepository.findAssignmentsWithExpiredLicense(LocalDate.now());

		for (Assignment assignment : expiredAssignments) {
			result.add(new NonCompliantDeviceResponse(assignment.getDeviceId(), "LICENSE_EXPIRED"));
		}

		return result;
	}

	@Override
	@Transactional
	public void decommissionDevice(String deviceId) {

		Device device = deviceRepository.findById(deviceId)
				.orElseThrow(() -> new DeviceNotFoundException("Device not found: " + deviceId));

		if (device.getStatus() == DeviceStatus.DECOMMISSIONED) {
			throw new RuntimeException("Device already decommissioned");
		}

		assignmentRepository.deleteByDeviceId(deviceId);

		device.setStatus(DeviceStatus.DECOMMISSIONED);
		device.setDecommissionedOn(LocalDate.now());
		device.setDecommissionReason("Decommissioned by admin");

		deviceRepository.save(device);

		auditLogService.createLog(
				"DEVICE",
				deviceId, 
				"DECOMMISSION",
				"Device decommissioned");
	}

	@Override
	public void deleteDevice(String deviceId) {
		
	    if (!deviceRepository.existsById(deviceId)) {
	        throw new RuntimeException("Device not found");
	    }
	    
		assignmentRepository.deleteByDeviceId(deviceId);
		deviceRepository.deleteById(deviceId);
		
	    auditLogService.createLog(
	            "DEVICE",
	            deviceId,
	            "DELETE",
	            "Device deleted"
	    );
		
	}

	@Override
	public boolean existsByDeviceId(String deviceId) {
		return deviceRepository.existsByDeviceId(deviceId);
	}

	@Override
	public List<DeviceLifecycleView> getDeviceLifecycle(String deviceId) {
		return deviceRepository.findDeviceLifecycle(deviceId);
	}

}
