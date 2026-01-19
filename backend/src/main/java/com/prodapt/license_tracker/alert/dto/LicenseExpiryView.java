package com.prodapt.license_tracker.alert.dto;

import java.time.LocalDate;

public interface LicenseExpiryView {
	String getLicenseKey();

	String getSoftwareName();

	String getVendorName();

	int getDevicesUsed();

	LocalDate getValidTo();
}
