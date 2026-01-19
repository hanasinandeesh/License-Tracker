package com.prodapt.license_tracker.assignment.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prodapt.license_tracker.assignment.dto.AssignmentViewDTO;
import com.prodapt.license_tracker.assignment.entity.Assignment;
import com.prodapt.license_tracker.assignment.exception.AssignmentAlreadyExistsException;
import com.prodapt.license_tracker.assignment.exception.AssignmentLimitExceededException;
import com.prodapt.license_tracker.assignment.repository.AssignmentRepository;
import com.prodapt.license_tracker.audit.service.AuditLogService;
import com.prodapt.license_tracker.device.exception.DeviceNotFoundException;
import com.prodapt.license_tracker.device.repository.DeviceRepository;
import com.prodapt.license_tracker.license.entity.License;
import com.prodapt.license_tracker.license.exception.LicenseNotFoundException;
import com.prodapt.license_tracker.license.repository.LicenseRepository;

@Service
@Transactional
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final DeviceRepository deviceRepository;
    private final LicenseRepository licenseRepository;
    private final AuditLogService auditLogService;

    public AssignmentServiceImpl(
            AssignmentRepository assignmentRepository,
            DeviceRepository deviceRepository,
            LicenseRepository licenseRepository,
            AuditLogService auditLogService) {

        this.assignmentRepository = assignmentRepository;
        this.deviceRepository = deviceRepository;
        this.licenseRepository = licenseRepository;
        this.auditLogService = auditLogService;
    }

    @Override
    public Assignment assignLicenseToDevice(String deviceId, String licenseKey) {

        if (!deviceRepository.existsById(deviceId)) {
            throw new DeviceNotFoundException("Device not found");
        }

        License license = licenseRepository.findById(licenseKey)
                .orElseThrow(() -> new LicenseNotFoundException("License not found"));

        if (assignmentRepository.existsByDeviceIdAndLicenseKey(deviceId, licenseKey)) {
            throw new AssignmentAlreadyExistsException("Already assigned");
        }

        long usage = assignmentRepository.countDevicesByLicenseKey(licenseKey);
        if (license.getMaxUsage() != null && usage >= license.getMaxUsage()) {
            throw new AssignmentLimitExceededException("Max usage exceeded");
        }

//        Assignment a = new Assignment();
//        a.setDeviceId(deviceId);
//        a.setLicenseKey(licenseKey);
//        a.setAssignedOn(LocalDate.now());
        
        Assignment assignment = new Assignment();
        assignment.setDeviceId(deviceId);
        assignment.setLicenseKey(licenseKey);
        assignment.setAssignedOn(LocalDate.now());

        Assignment saved = assignmentRepository.save(assignment); // ✅


        // ✅ AUDIT LOG
        auditLogService.createLog(
//                null,
                "ASSIGNMENT",
                saved.getAssignmentId().toString(),
                "CREATE",
                "License " + licenseKey + " assigned to device " + deviceId
        );

        return saved;
    }

    @Override
    public List<AssignmentViewDTO> getAssignments() {
        return assignmentRepository.fetchAssignmentView();
    }

    @Override
    public void revoke(Integer assignmentId) {
//        assignmentRepository.deleteById(assignmentId);
        if (!assignmentRepository.existsById(assignmentId)) {
            throw new RuntimeException("Assignment not found");
        }
        assignmentRepository.deleteById(assignmentId);
        
        // ✅ AUDIT LOG
        auditLogService.createLog(
//                null,
                "ASSIGNMENT",
                assignmentId.toString(),
                "DELETE",
                "License revoked from device"
        );
    }

    @Override
    public void removeAssignmentsByDevice(String deviceId) {
        assignmentRepository.deleteByDeviceId(deviceId);
    }

    @Override
    public long getLicenseUsage(String licenseKey) {
        return assignmentRepository.countDevicesByLicenseKey(licenseKey);
    }

    @Override
    public List<Assignment> getAssignmentsByDevice(String deviceId) {
        return assignmentRepository.findByDeviceId(deviceId);
    }

    @Override
    public List<Assignment> getAssignmentsByLicense(String licenseKey) {
        return assignmentRepository.findByLicenseKey(licenseKey);
    }
}
