package com.prodapt.license_tracker.software.service;

import java.util.List;

import com.prodapt.license_tracker.software.dto.SoftwareLifecycleDTO;
import com.prodapt.license_tracker.software.entity.SoftwareVersion;

public interface SoftwareVersionService {
	
    List<SoftwareVersion> getAll();

    List<SoftwareLifecycleDTO> getLifecycleByDevice(String deviceId);
    
    SoftwareVersion addSoftware(SoftwareVersion software);
    
    SoftwareVersion updateSoftware(Integer svId, SoftwareVersion software);
        
    void deleteSoftware(Integer svId);
}
