package com.prodapt.license_tracker.report.service;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prodapt.license_tracker.assignment.entity.Assignment;
import com.prodapt.license_tracker.assignment.repository.AssignmentRepository;
import com.prodapt.license_tracker.report.util.CsvGenerator;

@Service
public class LicenseReportServiceImpl implements LicenseReportService {

    private final AssignmentRepository assignmentRepository;

    public LicenseReportServiceImpl(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public void generateLicenseUsageCsv(PrintWriter writer) {

        CsvGenerator.writeLine(writer,
                List.of("Device ID", "License Key", "Assigned On"));

        assignmentRepository.findAll().forEach(a ->
                CsvGenerator.writeLine(writer,
                        List.of(
                                a.getDeviceId(),
                                a.getLicenseKey(),
                                String.valueOf(a.getAssignedOn())
                        ))
        );
    }
}





