package com.example.sfera_backend.controller;

import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.StatisticDTO;
import com.example.sfera_backend.service.StatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistic")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping
    @Operation(
            summary = "Admin statiskia",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<StatisticDTO>> getStatistic() {
        return ResponseEntity.ok(statisticService.getStatistic());
    }
}
