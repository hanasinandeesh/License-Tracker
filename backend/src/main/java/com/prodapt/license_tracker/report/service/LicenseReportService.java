package com.prodapt.license_tracker.report.service;

import java.io.PrintWriter;

public interface LicenseReportService {

    void generateLicenseUsageCsv(PrintWriter writer);
    
}

