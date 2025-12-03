package com.example.randompathtraversal;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// @RestController
public class FileController {

    // @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Create storage directory if not exists
            File dir = new File("storage");
            if (!dir.exists()) dir.mkdirs();

            Path path = Paths.get("storage", file.getOriginalFilename());
            file.transferTo(path.toFile());
            return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        }
    }

    // @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam String filename) {
        try {
            //
            File file = new File("storage/" + filename);
            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }
            byte[] content = Files.readAllBytes(file.toPath());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(content);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
