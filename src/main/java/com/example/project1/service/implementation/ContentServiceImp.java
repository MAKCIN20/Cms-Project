package com.example.project1.service.implementation;

import com.example.project1.entity.Content;
import com.example.project1.entity.License;
import com.example.project1.repository.ContentRepository;
import com.example.project1.repository.LicenseRepository;
import com.example.project1.service.ContentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor

public class ContentServiceImp implements ContentService {


    private final ContentRepository contentRepository;

    private LicenseRepository licenseRepository;


    public void addlicense(Long contentId, Long licenseId) {
        Content content = contentRepository.findOneById(contentId);
        License license = licenseRepository.findOneById(licenseId);
        if (content != null && license != null) {
            if (content.getLicenses().size() >= 1 && checkValid(contentId, licenseId)) {
                content.getLicenses().add(license);
                content.setLicenses(content.getLicenses());
                contentRepository.save(content);
            } else if (content.getLicenses().size() < 1) {
                content.getLicenses().add(license);
                content.setLicenses(content.getLicenses());
                contentRepository.save(content);
            }
        }
    }

    @Override
    public Boolean checkValid(Long contentId, Long licenseId) {
        Content content = contentRepository.findOneById(contentId);
        License license = licenseRepository.findOneById(licenseId);
        boolean isValid = true; //this value help to control under codes
        if (content != null && license != null) {
            if (content.getLicenses().size() >= 1) {

                for (License selectedLicense : content.getLicenses()) {
                    if ((checkOverlapping(license.getStartTime(), license.getEndTime(), selectedLicense.getStartTime(), selectedLicense.getEndTime()))) {
                        isValid = false;
                    }
                    if (isValid == false) {
                        break;
                    }

                }
            }
        }
        return isValid;
    }


    @Override
    public Content getContentById(Long contentId) {
        return contentRepository.findOneById(contentId);
    }

    @Override
    public Content insert(Content content) {
        content.setStatus("InProgress");
        return contentRepository.save(content);
    }


    @Override
    public void publish(Long contentId) {
        Content content = contentRepository.findOneById(contentId);
        if (content.getposterUrl() != null && content.getVideoUrl() != null && content.getLicenses().size() != 0) {
            content.setStatus("Published");
            contentRepository.save(content);
        }
    }


    @Override
    public ResponseEntity<String> updateContent(Content tempContent) {
        Content content = contentRepository.findOneById(tempContent.getId());
        System.out.println(tempContent.toString());
        content.setId(tempContent.getId());
        content.setName(tempContent.getName());
        content.setposterUrl(content.getposterUrl());
        content.setVideoUrl(tempContent.getVideoUrl());
        contentRepository.save(tempContent);
        return ResponseEntity.ok("content updated");
    }

    @Override
    public void deleteContent(Long contentID) {
        contentRepository.deleteById(contentID);
    }

    @Override
    public List<Content> getAll(String contentName) {
        if (contentName != null && !contentName.trim().equals("")) {
            return contentRepository.findByName(contentName);
        } else {
            return contentRepository.findAll();
        }

    }

    @Override
    public ResponseEntity<List<Content>> getAllContents() {
        List<Content> contents = new ArrayList<Content>();
        contentRepository.findAll().forEach(contents::add);
        return new ResponseEntity<>(contents, HttpStatus.OK);
    }


    public Boolean checkOverlapping(Long firstStartTime, Long firstEndTime, Long secondStartTime, Long secondEndTime) {
        return ((firstStartTime<secondStartTime) && (secondStartTime<firstEndTime)) || ((secondStartTime<firstStartTime) && (firstStartTime<secondEndTime)) ||
                ((secondStartTime<firstEndTime) && (secondEndTime<firstEndTime)) || ((firstStartTime<secondEndTime) && (firstStartTime>secondEndTime));
    }

}
