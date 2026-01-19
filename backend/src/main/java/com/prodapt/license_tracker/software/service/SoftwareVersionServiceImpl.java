package com.prodapt.license_tracker.software.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prodapt.license_tracker.audit.service.AuditLogService;
import com.prodapt.license_tracker.common.enums.SoftwareStatus;
import com.prodapt.license_tracker.device.repository.DeviceRepository;
import com.prodapt.license_tracker.software.dto.SoftwareLifecycleDTO;
import com.prodapt.license_tracker.software.entity.SoftwareVersion;
import com.prodapt.license_tracker.software.exception.DuplicateSoftwareException;
import com.prodapt.license_tracker.software.exception.SoftwareNotFoundException;
import com.prodapt.license_tracker.software.repository.SoftwareVersionRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SoftwareVersionServiceImpl implements SoftwareVersionService {

    private final SoftwareVersionRepository repository;

    public SoftwareVersionServiceImpl(SoftwareVersionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SoftwareLifecycleDTO> getLifecycleByDevice(String deviceId) {
        return repository.findLifecycleByDeviceId(deviceId);
    }

    @Override
    public SoftwareVersion addSoftware(SoftwareVersion software) {

        if (repository.existsByDeviceIdAndSoftwareName(
                software.getDeviceId(),
                software.getSoftwareName()
        )) {
            throw new RuntimeException("Software already exists for this device");
        }

        software.setLastChecked(LocalDate.now());
        return repository.save(software);
    }

    @Override
    public SoftwareVersion updateSoftware(Integer svId, SoftwareVersion software) {

        SoftwareVersion existing = repository.findById(svId)
                .orElseThrow(() -> new RuntimeException("Software not found"));

        existing.setCurrentVersion(software.getCurrentVersion());
        existing.setLatestVersion(software.getLatestVersion());
        existing.setStatus(software.getStatus());
        existing.setLastChecked(LocalDate.now());

        return repository.save(existing);
    }

    @Override
    public void deleteSoftware(Integer svId) {
        repository.deleteById(svId);
    }
    
    @Override
    public List<SoftwareVersion> getAll() {
        return repository.findAll(); // 🔥 THIS WAS MISSING OR BROKEN
    }
}
