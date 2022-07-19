package com.example.project1.controller;

import com.example.project1.entity.Content;
import com.example.project1.entity.License;
import com.example.project1.service.ContentService;
import com.example.project1.validator.ContentValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/contents")
@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class ContentController {

    private final ContentService contentService;
    private final ContentValidator contentValidator;

    @GetMapping
    public ResponseEntity<ResponseEntity<List<Content>>> getAll() {
        return ResponseEntity.ok(contentService.getAll());
    }

    @GetMapping({"/{contentId}"})
    public ResponseEntity<Content> getContent(@PathVariable Long contentId) {
        return new ResponseEntity<>(contentService.getContentById(contentId), HttpStatus.OK);
    }

    public ResponseEntity<Content> saveContent(@RequestBody Content content) {
        Content content1 = contentService.insert(content);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("content", "/api/v1/content" + content1.getId().toString());
        return new ResponseEntity<>(content1, httpHeaders, HttpStatus.CREATED);
    }


    @PostMapping
    public ResponseEntity insert(@RequestBody Content content) {
        contentService.insert(content);
        Date today = new Date();
        today.setHours(0);
        if (content.getLicenses() != null) {
            for (License license1 : content.getLicenses())
                if (license1.getStartTime().before(today) && license1.getEndTime().after(today) && content.getVideoUrl() != null && content.getposterUrl() != null) {
                    content.setStatus("Published");
                }
        }
        return ResponseEntity.ok("content is released");
    }

    @PostMapping("/{contentId}/licenses/{licenseId}/add")
    public ResponseEntity addlicense(@PathVariable("contentId") Long contentId, @PathVariable("licenseId") Long licenseId) {
        if(contentValidator.checkIsValid(contentId,licenseId)){
        contentService.addlicense(contentId, licenseId);
        return ResponseEntity.ok("License is added to content");}
        return ResponseEntity.ok("There is time overlap");
    }


    @PutMapping("/{id}")
    public ResponseEntity updateContent(@RequestBody Content content) {
        contentService.updateContent(content);
        return ResponseEntity.ok("Content is updated");
    }

    @PutMapping("/{contentId}/publish")
    public ResponseEntity publish(@PathVariable("contentId")Long contentId ) {
        contentService.publish(contentId);
        return ResponseEntity.ok("Content is published");
    }

    @DeleteMapping({"/{contentId}"})
    public ResponseEntity<Content> deleteContent(@PathVariable("contentId") Long contentId) {
        contentService.deleteContent(contentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{contentId}/uploadcontentposter")
    public ResponseEntity uploadPoster(@PathVariable("contentId") Long contentId, @RequestBody MultipartFile poster) {
        contentService.uploadPosterToContent(contentId, poster);
        return null;
    }

    @Scheduled(cron = "2 * * * * *")
    public void publishSchedule(Long contentId,Long licenseId){
        contentService.publishSchedule(contentId,licenseId);
    }
}
