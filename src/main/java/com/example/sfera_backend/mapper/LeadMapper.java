package com.example.sfera_backend.mapper;

import com.example.sfera_backend.dto.response.LeadResponse;
import com.example.sfera_backend.entity.Lead;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class LeadMapper {
    public LeadResponse toResponse(Lead lead){
        return new LeadResponse(
                lead.getFullName(),
                lead.getPhone(),
                lead.getCreatedAt()
        );
    }

    public List<LeadResponse> toResponseList(List<Lead> leads){
        return leads.stream().map(this::toResponse).toList();
    }
}
