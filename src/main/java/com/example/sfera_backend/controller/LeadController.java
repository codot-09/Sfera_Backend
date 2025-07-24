package com.example.sfera_backend.controller;

import com.example.sfera_backend.dto.request.LeadRequest;
import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.LeadResponse;
import com.example.sfera_backend.service.LeadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/lead")
@RequiredArgsConstructor
@Tag(name = "Lead", description = "Lead API")
public class LeadController {
    private final LeadService leadService;

    @PostMapping
    @Operation(
            summary = "Lead qoldirish"
    )
    public ResponseEntity<ApiResponse<String>> createLead(
            @RequestBody LeadRequest request
    ){
        return ResponseEntity.ok(leadService.createLead(request));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "ID bo'yicha leadni ko'rish",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LeadResponse>> getById(
            @PathVariable("id") UUID id
    ){
        return ResponseEntity.ok(leadService.getById(id));
    }

    @GetMapping
    @Operation(
            summary = "Barcha leadlarni ko'rish",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<LeadResponse>>> search(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String field,
            @RequestParam(required = false) boolean status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(leadService.search(startDate, endDate, field,status, pageable));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "leadni tasdiqlash",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ApiResponse<String>> confirmLead(
            @PathVariable("id") UUID id,
            @RequestParam boolean status
    ){
        return ResponseEntity.ok(leadService.confirmLead(id,status));
    }
}
