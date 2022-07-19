package com.example.project1.service.implementation;

import com.example.project1.entity.Content;
import com.example.project1.entity.License;
import com.example.project1.repository.ContentRepository;
import com.example.project1.repository.LicenseRepository;
import com.example.project1.service.LicenseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LicenseServiceImp implements LicenseService {
    private final LicenseRepository licenseRepository;
    private ContentRepository contentRepository;

    @Override
    public List<License> getAll() {
        return licenseRepository.findAll();
    }


    @Override
    public License getLicenseById(Long licenseId) {
        return licenseRepository.findOneById(licenseId);
    }

    @Override
    public License insert(License license) {
        return licenseRepository.save(license);
    }

    @Override
    public void updateLicense(License templicense) {
        License license = licenseRepository.findOneById(templicense.getId());
        System.out.println(templicense.toString());
        license.setId(templicense.getId());
        license.setName(templicense.getName());
        licenseRepository.save(templicense);
    }

    @Override
    public void deleteLicense(Long licenseID) {
        licenseRepository.deleteById(licenseID);
    }


}
