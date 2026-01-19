package com.prodapt.license_tracker.vendor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prodapt.license_tracker.vendor.entity.Vendor;
import com.prodapt.license_tracker.vendor.exception.VendorAlreadyExistsException;
import com.prodapt.license_tracker.vendor.exception.VendorNotFoundException;
import com.prodapt.license_tracker.vendor.repository.VendorRepository;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public Vendor addVendor(Vendor vendor) {

        if (vendorRepository.existsByVendorName(vendor.getVendorName())) {
            throw new VendorAlreadyExistsException(
                    "Vendor already exists: " + vendor.getVendorName());
        }

        return vendorRepository.save(vendor);
    }

    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @Override
    public Vendor getVendorById(Integer vendorId) {
        return vendorRepository.findById(vendorId)
                .orElseThrow(() ->
                        new VendorNotFoundException("Vendor not found: " + vendorId));
    }
}
