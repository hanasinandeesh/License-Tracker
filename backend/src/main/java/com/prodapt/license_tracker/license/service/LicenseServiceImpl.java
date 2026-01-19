package com.prodapt.license_tracker.license.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.prodapt.license_tracker.assignment.repository.AssignmentRepository;
import com.prodapt.license_tracker.audit.service.AuditLogService;
import com.prodapt.license_tracker.license.dto.LicenseExpiryAlertResponse;
import com.prodapt.license_tracker.license.dto.LicenseUsageResponse;
import com.prodapt.license_tracker.license.entity.License;
import com.prodapt.license_tracker.license.repository.LicenseRepository;
import com.prodapt.license_tracker.vendor.entity.Vendor;
import com.prodapt.license_tracker.vendor.repository.VendorRepository;


@Service
public class LicenseServiceImpl implements LicenseService {

    private final LicenseRepository licenseRepository;
    private final AssignmentRepository assignmentRepository;
    private final AuditLogService auditLogService;
    private final VendorRepository vendorRepository;


    public LicenseServiceImpl(
            LicenseRepository licenseRepository,
            AssignmentRepository assignmentRepository,
            AuditLogService auditLogService,
            VendorRepository vendorRepository) {

        this.licenseRepository = licenseRepository;
        this.assignmentRepository = assignmentRepository;
        this.auditLogService = auditLogService;
        this.vendorRepository = vendorRepository;
    }


    @Override
    public License addLicense(License license) {

        if (licenseRepository.existsById(license.getLicenseKey())) {
            throw new RuntimeException("License already exists");
        }

        if (license.getVendor() == null || license.getVendor().getVendorId() == null) {
            throw new RuntimeException("Vendor is required");
        }

        Vendor vendor = vendorRepository.findById(
                license.getVendor().getVendorId()
        ).orElseThrow(() ->
                new RuntimeException("Vendor not found")
        );

        license.setVendor(vendor);

        License saved = licenseRepository.save(license);

        auditLogService.createLog(
                "LICENSE",
                saved.getLicenseKey(),
                "CREATE",
                "License created"
        );

        return saved;
    }


    @Override
    public List<License> getAllLicenses() {
        return licenseRepository.findAll();
    }

    @Override
    public License getLicenseByKey(String licenseKey) {
        return licenseRepository.findById(licenseKey)
                .orElseThrow(() -> new RuntimeException("License not found"));
    }

    @Override
    public LicenseUsageResponse getLicenseUsage(String licenseKey) {

        License license = licenseRepository.findById(licenseKey)
                .orElseThrow(() ->
                        new RuntimeException("License not found: " + licenseKey));

        long usedCount = assignmentRepository.countDevicesByLicenseKey(licenseKey);

        return new LicenseUsageResponse(
                license.getLicenseKey(),
                license.getMaxUsage(),
                usedCount
        );
    }
    
    @Override
    public List<LicenseExpiryAlertResponse> getExpiringLicenses(int days) {

        LocalDate today = LocalDate.now();
        LocalDate toDate = today.plusDays(days);

        return licenseRepository
                .findExpiringLicenseView(today, toDate)
                .stream()
                .map(v -> {

                    long daysRemaining =
                            ChronoUnit.DAYS.between(today, v.getValidTo());

                    return new LicenseExpiryAlertResponse(
                            v.getLicenseKey(),
                            v.getSoftwareName(),
                            v.getVendorName(),
                            v.getDevicesUsed(),
                            v.getValidTo(),
                            daysRemaining
                    );
                })
                .collect(Collectors.toList());
    }
    
    
    
    @Override
    public void deleteLicense(String licenseKey) {

        if (!licenseRepository.existsById(licenseKey)) {
            throw new RuntimeException("License not found");
        }

        licenseRepository.deleteById(licenseKey);

        auditLogService.createLog(
                "LICENSE",
                licenseKey,
                "DELETE",
                "License deleted"
        );
    }

    
    
    @Override
    public License updateLicense(License license) {

        License existing = licenseRepository.findById(
                license.getLicenseKey()
        ).orElseThrow(() ->
                new RuntimeException("License not found")
        );

        existing.setSoftwareName(license.getSoftwareName());
        existing.setValidFrom(license.getValidFrom());
        existing.setValidTo(license.getValidTo());
        existing.setMaxUsage(license.getMaxUsage());

        
        License updated = licenseRepository.save(existing);

        auditLogService.createLog(
                "LICENSE",
                updated.getLicenseKey(),
                "UPDATE",
                "License updated"
        );

        return updated;
    }
    

}
