package com.example.project1.service;

import com.example.project1.entity.Content;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContentService {
    Content getContentById(Long Id);

    Content insert(Content content);

    void publish(Long contentId);

    ResponseEntity<String> updateContent(Content content);

    void deleteContent(Long contentId);

    List<Content> getAll(String contentName);

    void addlicense(Long contentId, Long licenseId);

    Boolean checkValid(Long contentId, Long licenseId);

    Boolean checkOverlapping(Long firstStartTime, Long firstEndTime, Long secondStartTime, Long secondEndTime);

    ResponseEntity<List<Content>> getAllContents();
}
