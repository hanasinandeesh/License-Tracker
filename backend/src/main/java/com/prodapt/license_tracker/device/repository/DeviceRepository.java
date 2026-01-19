package com.prodapt.license_tracker.device.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prodapt.license_tracker.device.dto.DeviceLifecycleView;
import com.prodapt.license_tracker.device.entity.Device;
import com.prodapt.license_tracker.report.dto.NonCompliantDeviceReportRow;

public interface DeviceRepository extends JpaRepository<Device, String> {

	boolean existsByIpAddress(String ipAddress);

	boolean existsByDeviceId(String deviceId);

	@Query("""
			    SELECT d
			    FROM Device d
			    LEFT JOIN Assignment a ON d.deviceId = a.deviceId
			    WHERE a.deviceId IS NULL
			""")
	List<Device> findDevicesWithoutLicense();

	@Query("""
			SELECT new com.prodapt.license_tracker.report.dto.NonCompliantDeviceReportRow(
			    d.deviceId,
			    d.model,
			    l.licenseKey,
			    l.softwareName,
			    l.validTo,
			    'LICENSE_EXPIRED'
			)
			FROM Assignment a
			JOIN Device d ON a.deviceId = d.deviceId
			JOIN License l ON a.licenseKey = l.licenseKey
			WHERE l.validTo < CURRENT_DATE
			""")
	List<NonCompliantDeviceReportRow> fetchExpiredLicenseDevices();

	@Query("""
			    SELECT new com.prodapt.license_tracker.device.dto.DeviceLifecycleView(
			        d.deviceId,
			        l.softwareName,
			        d.status,
			        d.decommissionedOn,
			        d.decommissionReason
			    )
			    FROM Assignment a
			    JOIN Device d ON a.deviceId = d.deviceId
			    JOIN License l ON a.licenseKey = l.licenseKey
			    WHERE d.deviceId = :deviceId
			""")
	List<DeviceLifecycleView> findDeviceLifecycle(@Param("deviceId") String deviceId);

}