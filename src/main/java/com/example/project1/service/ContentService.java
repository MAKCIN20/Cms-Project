package com.example.project1.service;

import com.example.project1.entity.Content;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface ContentService {
    Content getContentById(Long Id);

    Content insert(Content content);

    void publish(Long contentId);

    ResponseEntity<String> updateContent(Content content);

    void deleteContent(Long contentId);

    ResponseEntity<List<Content>> getAll();

    void addlicense(Long contentId, Long licenseId);

    Boolean checkValid(Long contentId, Long licenseId);

    Boolean checkOverlapping(Date start1, Date end1, Date start2, Date end2);

    ResponseEntity<List<Content>> getAllContents();

    void publishSchedule(Long contentId, Long licenseId);


    void uploadPosterToContent(Long contentId, MultipartFile poster);
}
