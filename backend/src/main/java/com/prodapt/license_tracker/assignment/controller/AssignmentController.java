package com.prodapt.license_tracker.assignment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.prodapt.license_tracker.assignment.dto.AssignmentRequest;
import com.prodapt.license_tracker.assignment.dto.AssignmentViewDTO;
import com.prodapt.license_tracker.assignment.entity.Assignment;
import com.prodapt.license_tracker.assignment.service.AssignmentService;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    // Assign license to device
    @PreAuthorize("hasAnyRole('ADMIN','ENGINEER')")
    @PostMapping
    public ResponseEntity<Assignment> assignLicenseToDevice(
            @RequestBody AssignmentRequest request) {

        Assignment assignment =
                assignmentService.assignLicenseToDevice(
                        request.getDeviceId(),
                        request.getLicenseKey());

        return ResponseEntity.status(HttpStatus.CREATED).body(assignment);
    }

    // Assignment table view (UI + Reports)
    @PreAuthorize("hasAnyRole('ADMIN','ENGINEER')")
    @GetMapping
    public List<AssignmentViewDTO> getAssignments() {
        return assignmentService.getAssignments();
    }

    // Get assignments by device (used by Device pages)
    @PreAuthorize("hasAnyRole('ADMIN','ENGINEER','AUDITOR')")
    @GetMapping("/device/{deviceId}")
    public List<Assignment> getAssignmentsByDevice(
            @PathVariable String deviceId) {

        return assignmentService.getAssignmentsByDevice(deviceId);
    }

    // Get assignments by license (used by License pages)
    @PreAuthorize("hasAnyRole('ADMIN','ENGINEER','AUDITOR')")
    @GetMapping("/licenses/{licenseKey}")
    public ResponseEntity<List<Assignment>> getAssignmentsByLicense(
            @PathVariable String licenseKey) {

        return ResponseEntity.ok(
                assignmentService.getAssignmentsByLicense(licenseKey));
    }

    // License usage (Alerts / Reports)
    @PreAuthorize("hasAnyRole('ADMIN','AUDITOR')")
    @GetMapping("/licenses/{licenseKey}/usage")
    public ResponseEntity<Long> getLicenseUsage(
            @PathVariable String licenseKey) {

        return ResponseEntity.ok(
                assignmentService.getLicenseUsage(licenseKey));
    }

    // Remove all assignments for a device
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/devices/{deviceId}")
    public ResponseEntity<Void> removeAssignmentsByDevice(
            @PathVariable String deviceId) {

        assignmentService.removeAssignmentsByDevice(deviceId);
        return ResponseEntity.noContent().build();
    }
    
    
    // Revoke assignment by assignmentId
    @PreAuthorize("hasAnyRole('ADMIN','ENGINEER')")
    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<Void> revokeAssignment(@PathVariable Integer assignmentId) {

        assignmentService.revoke(assignmentId);
        return ResponseEntity.noContent().build();
    }

}

