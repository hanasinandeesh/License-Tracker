package com.prodapt.license_tracker.vendor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prodapt.license_tracker.vendor.entity.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {

    boolean existsByVendorName(String vendorName);

    Optional<Vendor> findByVendorName(String vendorName);
    
}
