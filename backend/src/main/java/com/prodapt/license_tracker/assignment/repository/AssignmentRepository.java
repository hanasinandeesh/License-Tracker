package com.prodapt.license_tracker.assignment.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prodapt.license_tracker.assignment.dto.AssignmentViewDTO;
import com.prodapt.license_tracker.assignment.entity.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

	boolean existsByDeviceIdAndLicenseKey(String deviceId, String licenseKey);

	@Query("""
			    select count(a)
			    from Assignment a
			    where a.licenseKey = :licenseKey
			""")
	int countDevicesByLicenseKey(@Param("licenseKey") String licenseKey);

	List<Assignment> findByDeviceId(String deviceId);

	List<Assignment> findByLicenseKey(String licenseKey);

	void deleteByDeviceId(String deviceId);

	// ✅ SINGLE VIEW METHOD (used by UI + reports)
//	@Query("""
//			    SELECT new com.prodapt.license_tracker.assignment.dto.AssignmentViewDTO(
//			        a.assignmentId,
//			        a.deviceId,
//			        l.licenseKey,
//			        l.softwareName,
//			        a.assignedOn
//			    )
//			    FROM Assignment a
//			    JOIN License l ON a.licenseKey = l.licenseKey
//			""")
//	List<AssignmentViewDTO> fetchAssignmentView();
	@Query("""
			    SELECT new com.prodapt.license_tracker.assignment.dto.AssignmentViewDTO(
			        a.assignmentId,
			        a.deviceId,
			        a.licenseKey,
			        COALESCE(l.softwareName, 'DELETED LICENSE'),
			        a.assignedOn
			    )
			    FROM Assignment a
			    LEFT JOIN License l ON a.licenseKey = l.licenseKey
			""")
	List<AssignmentViewDTO> fetchAssignmentView();

	// 🔔 Used by Alerts module (KEEP)
	@Query("""
			    SELECT a
			    FROM Assignment a
			    JOIN License l ON a.licenseKey = l.licenseKey
			    WHERE l.validTo < :date
			""")
	List<Assignment> findAssignmentsWithExpiredLicense(LocalDate date);

	@Query("""
			    SELECT a
			    FROM Assignment a
			    JOIN License l ON a.licenseKey = l.licenseKey
			    WHERE l.validTo BETWEEN :start AND :end
			""")
	List<Assignment> findAssignmentsWithExpiringLicenses(LocalDate start, LocalDate end);

}
