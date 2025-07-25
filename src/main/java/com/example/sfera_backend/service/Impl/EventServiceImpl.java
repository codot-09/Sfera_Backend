package com.example.sfera_backend.service.Impl;

import com.example.sfera_backend.dto.request.EventRequest;
import com.example.sfera_backend.dto.response.EventDTO;
import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.entity.Event;
import com.example.sfera_backend.exception.ResourceNotFoundException;
import com.example.sfera_backend.mapper.EventMapper;
import com.example.sfera_backend.repository.EventRepository;
import com.example.sfera_backend.service.EventService;
import com.example.sfera_backend.service.cloud.CloudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public ApiResponse<String> addEvent(EventRequest event){

        if ( eventRepository.existsByNameIgnoreCase(event.getName())) {
            return ApiResponse.error("Bunday Nomli Tadbir allaqachon mavjud!!!");
        }

        Event event1 = Event.builder()
                .name(event.getName())
                .description(event.getDescription())
                .fileUrl(event.getFileUrl())
                .date(event.getDate())
                .hour(event.getHour())
                .build();
        eventRepository.save(event1);
        return ApiResponse.success(event1.toString());

    }

    @Override
    public ApiResponse<List<EventDTO>> getAllEvents() {
        List<EventDTO> list = eventRepository.findAll().stream().map(eventMapper::eventToEventDTO).toList();
        return ApiResponse.success(list);
    }

    @Override
    public ApiResponse<EventDTO> getEventById(UUID id) {
        Event event = eventRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Event topilmadi")
        );

        return ApiResponse.success(eventMapper.eventToEventDTO(event));
    }

    @Override
    public ApiResponse<String> updateEvent(UUID id, EventRequest eventDTO) {
        Event event = eventRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Event topilmadi")
        );

        boolean b = eventRepository.existsByNameIgnoreCaseAndIdNot(eventDTO.getName(), id);
        if (b) {
            return ApiResponse.error("Event already exists");
        }


        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setFileUrl(eventDTO.getFileUrl());
        event.setDate(eventDTO.getDate());
        event.setHour(eventDTO.getHour());
        eventRepository.save(event);

        return ApiResponse.success(event.toString());
    }

    @Override
    public ApiResponse<String> deleteEvent(UUID id) {
        Event event = eventRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Event topilmadi")
        );

        eventRepository.delete(event);
        return ApiResponse.success(event.toString());
    }
}
