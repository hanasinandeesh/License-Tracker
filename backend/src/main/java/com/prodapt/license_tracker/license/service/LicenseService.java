package com.prodapt.license_tracker.license.service;

import java.util.List;

import com.prodapt.license_tracker.license.dto.LicenseExpiryAlertResponse;
import com.prodapt.license_tracker.license.dto.LicenseUsageResponse;
import com.prodapt.license_tracker.license.entity.License;

public interface LicenseService {

    License addLicense(License license);

    List<License> getAllLicenses();

    License getLicenseByKey(String licenseKey);
    
    LicenseUsageResponse getLicenseUsage(String licenseKey);
    
    List<LicenseExpiryAlertResponse> getExpiringLicenses(int days);
    
    void deleteLicense(String licenseKey);

    License updateLicense(License license);
  

}