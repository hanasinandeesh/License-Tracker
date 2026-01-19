//package com.prodapt.license_tracker.alert.service;
//
//import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.stereotype.Service;
//
//import com.prodapt.license_tracker.alert.dto.DeviceExpiryAlertResponse;
//import com.prodapt.license_tracker.alert.dto.LicenseExpiryAlertResponse;
//import com.prodapt.license_tracker.alert.dto.LicenseExpiryView;
//import com.prodapt.license_tracker.assignment.entity.Assignment;
//import com.prodapt.license_tracker.assignment.repository.AssignmentRepository;
//import com.prodapt.license_tracker.license.entity.License;
//import com.prodapt.license_tracker.license.repository.LicenseRepository;
//import com.prodapt.license_tracker.vendor.repository.VendorRepository;
//
//@Service
//public class ExpiryAlertServiceImpl implements ExpiryAlertService {
//
//	private final LicenseRepository licenseRepository;
//	private final AssignmentRepository assignmentRepository;
//	private final VendorRepository vendorRepository;
//
//	public ExpiryAlertServiceImpl(LicenseRepository licenseRepository, AssignmentRepository assignmentRepository,
//			VendorRepository vendorRepository) {
//		this.licenseRepository = licenseRepository;
//		this.assignmentRepository = assignmentRepository;
//		this.vendorRepository = vendorRepository;
//	}
//
//	// 🔔 LICENSE ALERTS
//	@Override
//	public List<LicenseExpiryAlertResponse> getExpiringLicenses(int days) {
//
//	    LocalDate today = LocalDate.now();
//	    LocalDate threshold = today.plusDays(days);
//
//	    List<LicenseExpiryView> rows =
//	            licenseRepository.findExpiringLicenseView(today, threshold);
//
//	    return rows.stream().map(row -> {
//
//	        int remainingDays =
//	                (int) ChronoUnit.DAYS.between(today, row.getValidTo());
//
//	        LicenseExpiryAlertResponse dto =
//	                new LicenseExpiryAlertResponse();
//
//	        dto.setLicenseKey(row.getLicenseKey());
//	        dto.setSoftwareName(row.getSoftwareName());
//	        dto.setVendorName(row.getVendorName());
//	        dto.setDevicesUsed(row.getDevicesUsed());
//	        dto.setValidTo(row.getValidTo());
//	        dto.setRemainingDays(remainingDays);
//
//	        return dto;
//
//	    }).toList();
//	}
//
//	// 🔔 DEVICE ALERTS
//	@Override
//	public List<DeviceExpiryAlertResponse> getImpactedDevices(int days) {
//
//		LocalDate today = LocalDate.now();
//		LocalDate end = today.plusDays(days);
//
//		List<Assignment> assignments = assignmentRepository.findAssignmentsWithExpiringLicenses(today, end);
//
//		return assignments.stream().map(a -> new DeviceExpiryAlertResponse(a.getDeviceId(), a.getLicenseKey()))
//				.toList();
//	}
//}





package com.prodapt.license_tracker.alert.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prodapt.license_tracker.alert.dto.DeviceExpiryAlertResponse;
import com.prodapt.license_tracker.alert.dto.LicenseExpiryAlertResponse;
import com.prodapt.license_tracker.alert.dto.LicenseExpiryView;
import com.prodapt.license_tracker.assignment.entity.Assignment;
import com.prodapt.license_tracker.assignment.repository.AssignmentRepository;
import com.prodapt.license_tracker.license.repository.LicenseRepository;
import com.prodapt.license_tracker.vendor.repository.VendorRepository;

@Service
public class ExpiryAlertServiceImpl implements ExpiryAlertService {

    private final LicenseRepository licenseRepository;
    private final AssignmentRepository assignmentRepository;
    private final VendorRepository vendorRepository;

    public ExpiryAlertServiceImpl(
            LicenseRepository licenseRepository,
            AssignmentRepository assignmentRepository,
            VendorRepository vendorRepository) {

        this.licenseRepository = licenseRepository;
        this.assignmentRepository = assignmentRepository;
        this.vendorRepository = vendorRepository;
    }

    // 🔔 LICENSE ALERTS
    @Override
    public List<LicenseExpiryAlertResponse> getExpiringLicenses(int days) {

        LocalDate today = LocalDate.now();
        LocalDate threshold = today.plusDays(days);

        // Existing logic (unchanged)
        List<LicenseExpiryView> expiring =
                licenseRepository.findExpiringLicenseView(today, threshold);

        // NEW (expired licenses)
        List<LicenseExpiryView> expired =
                licenseRepository.findExpiredLicenseView(today);

        return java.util.stream.Stream
                .concat(expired.stream(), expiring.stream())
                .map(row -> {

                    int remainingDays =
                            (int) java.time.temporal.ChronoUnit.DAYS
                                    .between(today, row.getValidTo());

                    String alertLevel;
                    if (remainingDays < 0) {
                        alertLevel = "EXPIRED";
                    } else if (remainingDays <= 14) {
                        alertLevel = "CRITICAL";
                    } else {
                        alertLevel = "WARNING";
                    }

                    LicenseExpiryAlertResponse dto =
                            new LicenseExpiryAlertResponse();

                    dto.setLicenseKey(row.getLicenseKey());
                    dto.setSoftwareName(row.getSoftwareName());
                    dto.setVendorName(row.getVendorName());
                    dto.setDevicesUsed(row.getDevicesUsed());
                    dto.setValidTo(row.getValidTo());
                    dto.setRemainingDays(remainingDays);
                    dto.setAlertLevel(alertLevel);

                    return dto;

                }).toList();
    }

    // 🔔 DEVICE IMPACT ALERTS
    @Override
    public List<DeviceExpiryAlertResponse> getImpactedDevices(int days) {

        LocalDate today = LocalDate.now();
        LocalDate end = today.plusDays(days);

        List<Assignment> assignments =
                assignmentRepository.findAssignmentsWithExpiringLicenses(today, end);

        return assignments.stream()
                .map(a -> new DeviceExpiryAlertResponse(
                        a.getDeviceId(),
                        a.getLicenseKey()))
                .toList();
    }
}
