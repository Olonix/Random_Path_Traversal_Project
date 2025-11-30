package com.example.randompathtraversal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CoolFileController {

    private final FileController fileController = new FileController();

    @PostMapping("/coolupload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            FileService.validateFilename(filename);
            return fileController.uploadFile(file);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Validation failed: " + e.getMessage());
        }
    }

    @GetMapping("/cooldownload")
    public ResponseEntity<byte[]> downloadFile(@RequestParam String filename) {
        try {
            if (filename == null || !filename.matches("[a-zA-Z0-9_.-]+")) {
                throw new IllegalArgumentException("Invalid filename: " + filename);
            }
            return fileController.downloadFile(filename);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}