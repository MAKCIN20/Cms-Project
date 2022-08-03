package com.example.project1.repository;

import com.example.project1.entity.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {
    License findOneById(Long licenseId);
}
