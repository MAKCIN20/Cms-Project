package com.example.project1.validator;

import com.example.project1.service.ContentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ContentValidator {
    private final ContentService contentService;

    public Boolean checkIsValid(Long contentId, Long licenceId) {
        boolean isValid = contentService.checkValid(contentId, licenceId);
        if (isValid) {
            return true;
        }
        return false;
    }
}
