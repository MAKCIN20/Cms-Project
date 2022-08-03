package com.example.project1.controller;

import com.example.project1.entity.Content;
import com.example.project1.entity.License;
import com.example.project1.service.ContentService;
import com.example.project1.validator.ContentValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Controller
@Slf4j

@RequestMapping("/contents")
@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class ContentController {

    private final ContentService contentService;
    private final ContentValidator contentValidator;

    @GetMapping
    public ResponseEntity<List<Content>> getAll(@RequestParam(value = "contentName", required = false) String contentName) {
        return ResponseEntity.ok(contentService.getAll(contentName));
    }

    @GetMapping({"/{contentId}"})
    public ResponseEntity<Content> getContent(@PathVariable Long contentId) {
        return new ResponseEntity<>(contentService.getContentById(contentId), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity insert(@RequestBody Content content) {
        contentService.insert(content);
        LocalDate today = LocalDate.now();
        ZoneId zoneId = ZoneId.of("Europe/Paris"); // or: ZoneId.of("Europe/Oslo");
        long epoch = today.atStartOfDay(zoneId).toEpochSecond();
        if (content.getLicenses() != null) {
            for (License selectedLicense : content.getLicenses())
                if ((selectedLicense.getStartTime()<epoch && selectedLicense.getEndTime()>epoch) && (content.getVideoUrl() != null && content.getposterUrl() != null)) {
                    content.setStatus("Published");
                }
        }
        return ResponseEntity.ok("content is released");
    }

    @PostMapping("/{contentId}/licenses/{licenseId}/add")
    public ResponseEntity addlicense(@PathVariable("contentId") Long contentId, @PathVariable("licenseId") Long licenseId) {
        if (contentValidator.checkIsValid(contentId, licenseId)) {
            contentService.addlicense(contentId, licenseId);
            return ResponseEntity.ok("License is added to content");

        }
        return ResponseEntity.ok("There is time overlap");
    }


    @PutMapping("/{id}")
    public ResponseEntity updateContent(@RequestBody Content content) {
        contentService.updateContent(content);
        return ResponseEntity.ok("Content is updated");
    }

    @PutMapping("/{contentId}/publish")
    public ResponseEntity publish(@PathVariable("contentId") Long contentId) {
        contentService.publish(contentId);
        return ResponseEntity.ok("Content is published");
    }

    @DeleteMapping("/{contentId}")
    public ResponseEntity deleteContent(@PathVariable("contentId") Long contentId) {
        contentService.deleteContent(contentId);
        return ResponseEntity.ok("Deleted");
    }
}
