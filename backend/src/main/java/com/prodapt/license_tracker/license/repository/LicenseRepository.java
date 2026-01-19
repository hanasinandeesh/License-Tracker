package com.prodapt.license_tracker.license.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prodapt.license_tracker.alert.dto.LicenseExpiryView;
import com.prodapt.license_tracker.license.entity.License;

public interface LicenseRepository extends JpaRepository<License, String> {

	boolean existsByLicenseKey(String licenseKey);

	@Query("""
			   SELECT l FROM License l
			   WHERE l.validTo IS NOT NULL
			   AND l.validTo BETWEEN :start AND :end
			""")
	List<License> findByValidToBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

	@Query("""
			    SELECT l FROM License l
			    WHERE l.validTo <= :date
			""")
	List<License> findByValidToBefore(LocalDate date);

//	@Query(value = """
//			    SELECT
//			        l.license_key AS licenseKey,
//			        l.software_name AS softwareName,
//			        COALESCE(v.vendor_name, 'N/A') AS vendorName,
//			        COUNT(a.assignment_id) AS devicesUsed,
//			        l.valid_to AS validTo
//			    FROM license l
//			    LEFT JOIN vendor v ON v.vendor_id = l.vendor_id
//			    LEFT JOIN assignment a ON a.license_key = l.license_key
//			    WHERE l.valid_to BETWEEN :from AND :to
//			    GROUP BY l.license_key, l.software_name, v.vendor_name, l.valid_to
//			""", nativeQuery = true)
//	List<LicenseExpiryView> findExpiringLicenseView(@Param("from") LocalDate from, @Param("to") LocalDate to);

	@Query(value = """
			    SELECT
			        l.license_key AS licenseKey,
			        l.software_name AS softwareName,
			        COALESCE(v.vendor_name, 'N/A') AS vendorName,
			        COUNT(a.assignment_id) AS devicesUsed,
			        l.valid_to AS validTo
			    FROM license l
			    LEFT JOIN vendor v ON v.vendor_id = l.vendor_id
			    LEFT JOIN assignment a ON a.license_key = l.license_key
			    WHERE l.valid_to BETWEEN :from AND :to
			    GROUP BY l.license_key, l.software_name, v.vendor_name, l.valid_to
			""", nativeQuery = true)
	List<LicenseExpiryView> findExpiringLicenseView(@Param("from") LocalDate from, @Param("to") LocalDate to);
	
	
	@Query(value = """
	        SELECT
	            l.license_key AS licenseKey,
	            l.software_name AS softwareName,
	            COALESCE(v.vendor_name, 'N/A') AS vendorName,
	            COUNT(a.assignment_id) AS devicesUsed,
	            l.valid_to AS validTo
	        FROM license l
	        LEFT JOIN vendor v ON v.vendor_id = l.vendor_id
	        LEFT JOIN assignment a ON a.license_key = l.license_key
	        WHERE l.valid_to < :today
	        GROUP BY l.license_key, l.software_name, v.vendor_name, l.valid_to
	    """, nativeQuery = true)
	List<LicenseExpiryView> findExpiredLicenseView(
	        @Param("today") LocalDate today
	);


}