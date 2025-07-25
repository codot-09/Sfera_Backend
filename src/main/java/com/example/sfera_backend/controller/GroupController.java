package com.example.sfera_backend.controller;

import com.example.sfera_backend.dto.request.GroupRequest;
import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.GroupResponse;
import com.example.sfera_backend.jwt.RequireToken;
import com.example.sfera_backend.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
@Tag(name = "Group", description = "Group API")
public class GroupController {
    private final GroupService groupService;

    @PostMapping(value = "/create")
    @Operation(
            summary = "Qabul uchun guruh ochish",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @RequireToken
    public ResponseEntity<ApiResponse<String>> openGroup(
            @RequestBody GroupRequest request
    ) throws IOException {
        return ResponseEntity.ok(groupService.openGroup(request));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Guruhni yopish",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @RequireToken
    public ResponseEntity<ApiResponse<String>> closeGroup(
            @PathVariable("id") UUID id
    ){
        return ResponseEntity.ok(groupService.closeGroup(id));
    }

    @GetMapping
    @Operation(
            summary = "Barcha guruhlarni ko'rish"
    )
    public ResponseEntity<ApiResponse<List<GroupResponse>>> getAll(
            @RequestParam(defaultValue = "true") boolean status
    ){
        return ResponseEntity.ok(groupService.getAll(status));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "ID bo'yicha guruhni ko'rish"
    )
    public ResponseEntity<ApiResponse<GroupResponse>> getById(
            @PathVariable("id") UUID id
    ){
        return ResponseEntity.ok(groupService.getById(id));
    }
}
