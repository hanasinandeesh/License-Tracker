package com.prodapt.license_tracker.report.service;

import java.io.PrintWriter;

public interface DeviceReportService {

    void generateNonCompliantDevicesCsv(PrintWriter writer);

}
