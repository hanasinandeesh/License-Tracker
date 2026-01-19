package com.prodapt.license_tracker.report.service;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prodapt.license_tracker.assignment.entity.Assignment;
import com.prodapt.license_tracker.assignment.repository.AssignmentRepository;
import com.prodapt.license_tracker.report.util.CsvGenerator;

@Service
public class DeviceReportServiceImpl implements DeviceReportService {

    private final AssignmentRepository assignmentRepository;

    public DeviceReportServiceImpl(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public void generateNonCompliantDevicesCsv(PrintWriter writer) {

        CsvGenerator.writeLine(writer,
                List.of("Device ID", "Reason"));

        assignmentRepository
                .findAssignmentsWithExpiredLicense(LocalDate.now())
                .forEach(a ->
                        CsvGenerator.writeLine(writer,
                                List.of(a.getDeviceId(), "LICENSE_EXPIRED"))
                );
    }
}
