package com.example.sfera_backend.service.Impl;

import com.example.sfera_backend.bot.BotCode;
import com.example.sfera_backend.dto.request.LeadRequest;
import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.LeadResponse;
import com.example.sfera_backend.entity.Group;
import com.example.sfera_backend.entity.Lead;
import com.example.sfera_backend.exception.ResourceNotFoundException;
import com.example.sfera_backend.mapper.LeadMapper;
import com.example.sfera_backend.repository.GroupRepository;
import com.example.sfera_backend.repository.LeadRepository;
import com.example.sfera_backend.service.LeadService;
import com.example.sfera_backend.specification.LeadSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LeadServiceImpl implements LeadService {
    private final LeadMapper leadMapper;
    private final LeadRepository leadRepository;
    private final GroupRepository groupRepository;
    private final BotCode bot;

    @Value("${site.link}")
    private String link;

    @Override
    public ApiResponse<String> createLead(LeadRequest leadRequest) {
        if(leadRepository.existsByPhone(leadRequest.getPhoneNumber())){
            return ApiResponse.error("Lead allaqachon mavjud");
        }

        Group group = groupRepository.findById(leadRequest.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Guruh topilmadi"));

        Lead newLead = Lead.builder()
                .fullName(leadRequest.getFullName())
                .phone(leadRequest.getPhoneNumber())
                .build();

        leadRepository.save(newLead);

        bot.reminder("Yangi lead: " + link);

        return ApiResponse.success("Lead muvaffaqiyatli yaratildi");
    }

    @Override
    public ApiResponse<LeadResponse> getById(UUID id) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lead topilmadi"));

        return ApiResponse.success(leadMapper.toResponse(lead));
    }

    @Override
    public ApiResponse<Page<LeadResponse>> search(LocalDate startDate,
                                                  LocalDate endDate,
                                                  String field,
                                                  boolean confirmed,
                                                  Pageable pageable
    ) {
        Page<Lead> leads = leadRepository.findAll(LeadSpecification.build(field,startDate,endDate,confirmed),pageable);
        Page<LeadResponse> leadResponses = leads.map(leadMapper::toResponse);

        return ApiResponse.success(leadResponses);
    }

    @Override
    @Transactional
    public ApiResponse<String> confirmLead(UUID leadId,boolean status) {
        Lead lead = leadRepository.findById(leadId)
                .orElseThrow(() -> new ResourceNotFoundException("Lead topilmadi"));

        Group group = lead.getGroup();
        int studentCount = group.getCurrentStudentCount();

        group.setCurrentStudentCount(studentCount++);
        groupRepository.save(group);

        lead.setConfirmed(true);
        leadRepository.save(lead);

        return ApiResponse.success("Lead tasqidlaydi");
    }
}
