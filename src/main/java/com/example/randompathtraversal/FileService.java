package com.example.randompathtraversal;

public class FileService {

    public static String validateFilename(String filename) {
        if (filename == null || !filename.matches("[a-zA-Z0-9_.-]+")) {
            throw new IllegalArgumentException("Invalid filename: " + filename);
        }
        return filename;
    }
}