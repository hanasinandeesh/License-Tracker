package com.prodapt.license_tracker.report.service;

import java.io.PrintWriter;

public interface AuditReportService {

    void generateAuditCsv(PrintWriter writer);
    
}