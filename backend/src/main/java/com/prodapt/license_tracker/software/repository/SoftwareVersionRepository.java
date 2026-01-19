package com.prodapt.license_tracker.software.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.prodapt.license_tracker.software.dto.SoftwareLifecycleDTO;
import com.prodapt.license_tracker.software.entity.SoftwareVersion;

public interface SoftwareVersionRepository
extends JpaRepository<SoftwareVersion, Integer> {

// ✅ FIXED: single correct method, LEFT JOIN
	@Query("""
		    SELECT new com.prodapt.license_tracker.software.dto.SoftwareLifecycleDTO(
		        sv.svId,
		        sv.deviceId,
		        sv.softwareName,
		        sv.currentVersion,
		        sv.latestVersion,
		        sv.status,
		        d.status
		    )
		    FROM SoftwareVersion sv
		    LEFT JOIN Device d ON sv.deviceId = d.deviceId
		""")
		List<SoftwareLifecycleDTO> fetchAllLifecycle();

// ✅ Needed for device-specific lifecycle
	@Query("""
		    SELECT new com.prodapt.license_tracker.software.dto.SoftwareLifecycleDTO(
		        sv.svId,
		        sv.deviceId,
		        sv.softwareName,
		        sv.currentVersion,
		        sv.latestVersion,
		        sv.status,
		        d.status
		    )
		    FROM SoftwareVersion sv
		    LEFT JOIN Device d ON sv.deviceId = d.deviceId
		    WHERE sv.deviceId = :deviceId
		""")
		List<SoftwareLifecycleDTO> findLifecycleByDeviceId(String deviceId);


boolean existsByDeviceIdAndSoftwareName(String deviceId, String softwareName);
}
