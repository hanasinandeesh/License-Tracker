package com.prodapt.license_tracker.assignment.service;

import java.util.List;

import com.prodapt.license_tracker.assignment.dto.AssignmentViewDTO;
import com.prodapt.license_tracker.assignment.entity.Assignment;

public interface AssignmentService {


    Assignment assignLicenseToDevice(String deviceId, String licenseKey);

    List<AssignmentViewDTO> getAssignments(); // ← used by UI + Reports

    void revoke(Integer assignmentId);

    void removeAssignmentsByDevice(String deviceId);

    long getLicenseUsage(String licenseKey);

    List<Assignment> getAssignmentsByDevice(String deviceId);

    List<Assignment> getAssignmentsByLicense(String licenseKey);
}

