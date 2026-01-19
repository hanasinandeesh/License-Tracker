package com.prodapt.license_tracker.alert.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.prodapt.license_tracker.alert.dto.DeviceExpiryAlertResponse;
import com.prodapt.license_tracker.alert.dto.LicenseExpiryAlertResponse;
import com.prodapt.license_tracker.alert.service.ExpiryAlertService;

@RestController
@RequestMapping("/api/alerts")
public class ExpiryAlertController {

	private final ExpiryAlertService expiryAlertService;

	public ExpiryAlertController(ExpiryAlertService expiryAlertService) {
		this.expiryAlertService = expiryAlertService;
	}

	@GetMapping("/licenses")
	public List<LicenseExpiryAlertResponse> getExpiringLicenses(@RequestParam(defaultValue = "30") int days) {

		return expiryAlertService.getExpiringLicenses(days);
	}

	@GetMapping("/devices")
	public List<DeviceExpiryAlertResponse> getImpactedDevices(@RequestParam(defaultValue = "30") int days) {

		return expiryAlertService.getImpactedDevices(days);
	}
}
