package com.prodapt.license_tracker.alert.service;

import java.util.List;

import com.prodapt.license_tracker.alert.dto.DeviceExpiryAlertResponse;
import com.prodapt.license_tracker.alert.dto.LicenseExpiryAlertResponse;

public interface ExpiryAlertService {

    List<LicenseExpiryAlertResponse> getExpiringLicenses(int days);

    List<DeviceExpiryAlertResponse> getImpactedDevices(int days);
}
