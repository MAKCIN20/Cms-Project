package com.example.project1.service;

import com.example.project1.entity.License;

import java.util.List;

public interface LicenseService {

  
    License getLicenseById(Long Id);

    License insert(License license);

    void updateLicense(License license);

    void deleteLicense(Long licenseId);


    List<License> getAll();

}
