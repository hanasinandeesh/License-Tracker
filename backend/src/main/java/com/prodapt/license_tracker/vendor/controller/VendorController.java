package com.prodapt.license_tracker.vendor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prodapt.license_tracker.vendor.entity.Vendor;
import com.prodapt.license_tracker.vendor.service.VendorService;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping
    public ResponseEntity<Vendor> addVendor(@RequestBody Vendor vendor) {
        return new ResponseEntity<>(
                vendorService.addVendor(vendor),
                HttpStatus.CREATED);
    }

    @GetMapping
    public List<Vendor> getAllVendors() {
        return vendorService.getAllVendors();
    }

    @GetMapping("/{id}")
    public Vendor getVendorById(@PathVariable Integer id) {
        return vendorService.getVendorById(id);
    }
}
