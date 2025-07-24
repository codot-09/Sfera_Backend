package com.example.sfera_backend.service.cloud;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudService {
    String uploadFile(MultipartFile file) throws IOException;
}
