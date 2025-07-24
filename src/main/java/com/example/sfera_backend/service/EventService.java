package com.example.sfera_backend.service;

import com.example.sfera_backend.dto.request.EventRequest;
import com.example.sfera_backend.dto.response.EventDTO;
import com.example.sfera_backend.dto.response.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface EventService {

    ApiResponse<String>addEvent(EventRequest event, MultipartFile file) throws IOException;
    ApiResponse<List<EventDTO>> getAllEvents();
    ApiResponse<EventDTO> getEventById(UUID id);
    ApiResponse<String> updateEvent(UUID id, EventRequest event, MultipartFile file) throws IOException;
    ApiResponse<String> deleteEvent(UUID id);
}
