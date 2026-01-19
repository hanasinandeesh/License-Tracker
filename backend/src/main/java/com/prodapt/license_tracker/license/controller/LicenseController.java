package com.prodapt.license_tracker.license.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prodapt.license_tracker.license.dto.LicenseExpiryAlertResponse;
import com.prodapt.license_tracker.license.dto.LicenseUsageResponse;
import com.prodapt.license_tracker.license.entity.License;
import com.prodapt.license_tracker.license.service.LicenseService;
import com.prodapt.license_tracker.vendor.entity.Vendor;

@RestController
@RequestMapping("/api/licenses")
public class LicenseController {

    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<License> addLicense(@RequestBody License license) {
        License saved = licenseService.addLicense(license);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','ENGINEER','AUDITOR')")
    @GetMapping
    public ResponseEntity<List<License>> getAllLicenses() {
        return ResponseEntity.ok(licenseService.getAllLicenses());
    }

    @PreAuthorize("hasAnyRole('ADMIN','ENGINEER','AUDITOR')")
    @GetMapping("/{licenseKey}")
    public ResponseEntity<License> getLicenseByKey(@PathVariable String licenseKey) {
        return ResponseEntity.ok(licenseService.getLicenseByKey(licenseKey));
    }
    
    @PreAuthorize("hasAnyRole('ADMIN','AUDITOR')")
    @GetMapping("/{licenseKey}/usage")
    public LicenseUsageResponse getLicenseUsage(
            @PathVariable String licenseKey) {

        return licenseService.getLicenseUsage(licenseKey);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN','AUDITOR')")
    @GetMapping("/expiring")
    public List<LicenseExpiryAlertResponse> getExpiringLicenses(
            @RequestParam int days) {

        return licenseService.getExpiringLicenses(days);
    }
    
    @DeleteMapping("/{licenseKey}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteLicense(@PathVariable String licenseKey) {
        licenseService.deleteLicense(licenseKey);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{licenseKey}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<License> updateLicense(
            @PathVariable String licenseKey,
            @RequestBody License license) {

        license.setLicenseKey(licenseKey);
        return ResponseEntity.ok(licenseService.updateLicense(license));
    }

}
