package com.prodapt.license_tracker.software.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.prodapt.license_tracker.software.dto.SoftwareLifecycleDTO;
import com.prodapt.license_tracker.software.entity.SoftwareVersion;
import com.prodapt.license_tracker.software.repository.SoftwareVersionRepository;
import com.prodapt.license_tracker.software.service.SoftwareVersionService;

@RestController
@RequestMapping("/api/software")
public class SoftwareVersionController {

	private final SoftwareVersionService softwareVersionService;
	private final SoftwareVersionRepository softwareVersionRepository;

	public SoftwareVersionController(SoftwareVersionService softwareVersionService,
			SoftwareVersionRepository softwareVersionRepository) {
		this.softwareVersionService = softwareVersionService;
		this.softwareVersionRepository = softwareVersionRepository;
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','ENGINEER','AUDITOR')")
	public List<SoftwareLifecycleDTO> getAllSoftware() {
	    return softwareVersionRepository.fetchAllLifecycle();
	}

	@PreAuthorize("hasAnyRole('ADMIN','ENGINEER','AUDITOR')")
	@GetMapping("/device/{deviceId}")
	public List<SoftwareLifecycleDTO> getDeviceSoftware(@PathVariable String deviceId) {
		return softwareVersionService.getLifecycleByDevice(deviceId);
	}

	@PreAuthorize("hasAnyRole('ADMIN','ENGINEER')")
	@PostMapping
	public SoftwareVersion addSoftware(@RequestBody SoftwareVersion software) {
		return softwareVersionService.addSoftware(software);
	}

	@PreAuthorize("hasAnyRole('ADMIN','ENGINEER')")
	@PutMapping("/{svId}")
	public SoftwareVersion updateSoftware(@PathVariable Integer svId, @RequestBody SoftwareVersion software) {
		return softwareVersionService.updateSoftware(svId, software);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{svId}")
	public void deleteSoftware(@PathVariable Integer svId) {
		softwareVersionService.deleteSoftware(svId);
	}

}
