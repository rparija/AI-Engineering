package com.telusko.tutor.controller;

import com.telusko.tutor.service.RAGService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/ingest")
@RequiredArgsConstructor
public class IngestionController {

    private final RAGService ragService;

    @PostMapping
    public ResponseEntity<String> ingestFile(@RequestParam("file") MultipartFile file,
            @RequestParam("courseId") String courseId,
            @RequestParam("section") String section) {
        try {
            ragService.ingestContent(new ByteArrayResource(file.getBytes()), courseId, section);
            return ResponseEntity.ok("Ingestion successful");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error processing file: " + e.getMessage());
        }
    }
}
