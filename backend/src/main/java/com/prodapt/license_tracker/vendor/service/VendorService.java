package com.prodapt.license_tracker.vendor.service;

import java.util.List;

import com.prodapt.license_tracker.vendor.entity.Vendor;
import com.prodapt.license_tracker.vendor.repository.VendorRepository;

public interface VendorService {

    Vendor addVendor(Vendor vendor);

    List<Vendor> getAllVendors();

    Vendor getVendorById(Integer vendorId);
   
}
