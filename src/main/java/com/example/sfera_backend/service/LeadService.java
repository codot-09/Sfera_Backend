package com.example.sfera_backend.service;

import com.example.sfera_backend.dto.request.LeadRequest;
import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.LeadResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.UUID;

public interface LeadService {
    ApiResponse<String> createLead(LeadRequest leadRequest);
    ApiResponse<LeadResponse> getById(UUID id);
    ApiResponse<Page<LeadResponse>> search(LocalDate startDate, LocalDate endDate, String field,boolean confirmed, Pageable pageable);
    ApiResponse<String> confirmLead(UUID leadId,boolean status);
}
