package com.example.sfera_backend.controller;

import com.example.sfera_backend.jwt.RequireToken;
import com.example.sfera_backend.service.cloud.CloudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
@Tag(name = "File", description = "File API")
public class FileController {
    private final CloudService cloudService;

    @PostMapping(value = "/upload",consumes = "multipart/form-data" )
    @Operation(
            summary = "Fayl yuklash",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @RequireToken
    public ResponseEntity<String> uploadFile(
            @RequestParam("file")MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(cloudService.uploadFile(file));
    }
}
