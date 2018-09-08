package com.retro.retrobe.controller;

import com.retro.retrobe.model.RetroTemplate;
import com.retro.retrobe.payload.ApiResponse;
import com.retro.retrobe.payload.TemplateRequest;
import com.retro.retrobe.security.JwtTokenProvider;
import com.retro.retrobe.service.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class TemplateController {
    private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);
    private TemplateService templateService;
    @Autowired
    public TemplateController(final TemplateService templateService, final JwtTokenProvider tokenProvider) {
        this.templateService = templateService;
    }

    @GetMapping("templates")
    public ResponseEntity<?> getAllTemplates() {
        try {
            List<RetroTemplate> templates = templateService.getTemplates();
            return new ResponseEntity<>(new ApiResponse<>(true, templates, "templates", 200), HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return  new ResponseEntity<>(new ApiResponse(false, "failed to retrieve", 500), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("templates")
    public ResponseEntity<?> saveTemplate(@RequestBody TemplateRequest request) {
        try {
            templateService.saveTemplate(request);
            return  new ResponseEntity<>(new ApiResponse(true, "RetroTemplate Saved", 200), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return  new ResponseEntity<>(new ApiResponse(false, "failed to saved", 500), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
