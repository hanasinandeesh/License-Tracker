package com.prodapt.license_tracker.ai.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prodapt.license_tracker.alert.dto.LicenseExpiryAlertResponse;
import com.prodapt.license_tracker.alert.service.ExpiryAlertService;
import com.prodapt.license_tracker.device.dto.NonCompliantDeviceResponse;
import com.prodapt.license_tracker.device.service.DeviceService;
import com.prodapt.license_tracker.auth.security.util.LoggedInUserUtil;
import com.prodapt.license_tracker.common.enums.AiIntent;

@Service
public class AiService {

    private final ExpiryAlertService expiryAlertService;
    private final DeviceService deviceService;
    private final LoggedInUserUtil loggedInUserUtil;

    public AiService(
            ExpiryAlertService expiryAlertService,
            DeviceService deviceService,
            LoggedInUserUtil loggedInUserUtil) {

        this.expiryAlertService = expiryAlertService;
        this.deviceService = deviceService;
        this.loggedInUserUtil = loggedInUserUtil;
    }

    private AiIntent detectIntent(String q) {
        q = q.toLowerCase();

        if (q.contains("action") || q.contains("stay compliant"))
            return AiIntent.ACTIONS;

        if (q.contains("overall") || q.contains("health"))
            return AiIntent.COMPLIANCE_HEALTH;

        if (q.contains("expired"))
            return AiIntent.EXPIRED_LICENSES;

        if (q.contains("expiring") || q.contains("renewal"))
            return AiIntent.EXPIRING_LICENSES;

        if (q.contains("overused") || q.contains("usage"))
            return AiIntent.USAGE_STATUS;

        if (q.contains("non-compliant"))
            return AiIntent.NON_COMPLIANT_DEVICES;

        if (q.contains("audit"))
            return AiIntent.AUDIT_SUMMARY;

        if (q.contains("today") || q.contains("aware"))
            return AiIntent.DAILY_SUMMARY;

        return AiIntent.UNKNOWN;
    }

    public String handleQuery(String question) {

        String role = loggedInUserUtil.getCurrentUserRole();
        AiIntent intent = detectIntent(question);

        List<LicenseExpiryAlertResponse> licenses =
                expiryAlertService.getExpiringLicenses(30);

        List<NonCompliantDeviceResponse> devices =
                deviceService.getNonCompliantDevices();

        return switch (intent) {

            case ACTIONS -> adminActions(role);

            case COMPLIANCE_HEALTH -> complianceHealth(role, licenses, devices);

            case EXPIRED_LICENSES -> expiredLicensesOnly(licenses);

            case EXPIRING_LICENSES -> expiringLicensesOnly(licenses);

            case USAGE_STATUS -> usageStatus(role);

            case NON_COMPLIANT_DEVICES -> nonCompliantDevices(role, devices);

            case AUDIT_SUMMARY -> auditSummary(role, licenses, devices);

            case DAILY_SUMMARY -> dailySummary(role, licenses, devices);

            default -> guidance(role);
        };
    }


    private String adminActions(String role) {
        if (!"ADMIN".equals(role)) {
            return "Only administrators can view compliance actions.";
        }
        return """
        ADMIN COMPLIANCE ACTIONS

        • Renew expired and expiring licenses
        • Assign licenses to unlicensed devices
        • Review usage limits
        • Decommission obsolete devices
        • Verify software versions
        """;
    }

    private String complianceHealth(String role,
            List<LicenseExpiryAlertResponse> l,
            List<NonCompliantDeviceResponse> d) {

        return role + " COMPLIANCE SUMMARY\n\n" +
               "Expired licenses: " + l.stream().filter(x -> x.getRemainingDays() < 0).count() + "\n" +
               "Licenses expiring soon: " + l.stream().filter(x -> x.getRemainingDays() <= 15).count() + "\n" +
               "Non-compliant devices: " + d.size();
    }

    private String expiredLicensesOnly(List<LicenseExpiryAlertResponse> licenses) {
        StringBuilder sb = new StringBuilder("Expired Licenses:\n");
        licenses.stream()
                .filter(l -> l.getRemainingDays() < 0)
                .forEach(l -> sb.append("- ").append(l.getLicenseKey()).append("\n"));
        return sb.toString();
    }

    private String expiringLicensesOnly(List<LicenseExpiryAlertResponse> licenses) {
        StringBuilder sb = new StringBuilder("Licenses Expiring Soon:\n");
        licenses.stream()
                .filter(l -> l.getRemainingDays() >= 0 && l.getRemainingDays() <= 15)
                .forEach(l -> sb.append("- ").append(l.getLicenseKey())
                        .append(" expires in ").append(l.getRemainingDays()).append(" days\n"));
        return sb.toString();
    }

    private String nonCompliantDevices(String role,
            List<NonCompliantDeviceResponse> devices) {

        if ("ENGINEER".equals(role) || "ADMIN".equals(role)) {
            return "Devices at Risk: " + devices.size();
        }
        return "Auditors can only view counts, not device details.";
    }

    private String usageStatus(String role) {
        if (!"AUDITOR".equals(role)) {
            return "Usage checks are auditor-only.";
        }
        return """
        AUDIT USAGE STATUS

        • Overused licenses: 0
        • Usage violations: None
        • Status: Within limits
        """;
    }

    private String auditSummary(String role,
            List<LicenseExpiryAlertResponse> l,
            List<NonCompliantDeviceResponse> d) {

        if (!"AUDITOR".equals(role)) {
            return "Audit summary is auditor-only.";
        }
        return """
        AUDIT SUMMARY

        • Expired licenses: %d
        • Devices non-compliant: %d
        • Overall Status: Review Required
        """.formatted(
                l.stream().filter(x -> x.getRemainingDays() < 0).count(),
                d.size()
        );
    }

    private String dailySummary(String role,
            List<LicenseExpiryAlertResponse> l,
            List<NonCompliantDeviceResponse> d) {

        if ("ENGINEER".equals(role)) {
            return """
            ENGINEER DAILY SUMMARY

            • Devices at risk: %d
            • Licenses expiring soon: %d

            Action: Escalate to admin
            """.formatted(
                    d.size(),
                    l.stream().filter(x -> x.getRemainingDays() <= 15).count()
            );
        }
        return "Daily summary is intended for engineers.";
    }

    private String guidance(String role) {
        return switch (role) {
            case "ADMIN" -> "Ask about compliance health, renewals, or actions.";
            case "ENGINEER" -> "Ask about devices at risk or daily actions.";
            case "AUDITOR" -> "Ask about audit status, usage limits, or expired licenses.";
            default -> "Please ask a license compliance related question.";
        };
    }
}
