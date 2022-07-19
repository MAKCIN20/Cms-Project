package com.example.project1.controller;


import com.example.project1.entity.License;
import com.example.project1.service.LicenseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/licenses")
@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class LicenseController {

    private LicenseService licenseService;

    @GetMapping
    public ResponseEntity<List<License>> getAll() {
        return ResponseEntity.ok(licenseService.getAll());
    }


    public ResponseEntity<License> saveLicense(@RequestBody License license) {
        License license1 = licenseService.insert(license);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("license", "/api/v1/license" + license1.getId().toString());
        return new ResponseEntity<>(license1, httpHeaders, HttpStatus.CREATED);
    }

    @PostMapping
    public Object insert(@RequestBody License license) {

        if (license.getStartTime() != null && license.getEndTime() != null) {
            licenseService.insert(license);
            return ResponseEntity.ok("Ok");
        } else if (license.getStartTime() == null || license.getEndTime() == null) {
            return ResponseEntity.ok("Without start time or end time you cannot create license");
        }
        return ResponseEntity.ok();
    }


    @PutMapping
    public ResponseEntity updateLicense(@RequestBody License license) {
        licenseService.updateLicense(license);
        return ResponseEntity.ok("OK");

    }

    @DeleteMapping({"/{licenseId}"})
    public ResponseEntity<License> deleteLicense(@PathVariable("licenseId") Long licenseId) {
        licenseService.deleteLicense(licenseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

