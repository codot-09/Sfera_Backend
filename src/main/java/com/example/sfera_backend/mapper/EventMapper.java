package com.example.sfera_backend.mapper;

import com.example.sfera_backend.dto.response.EventDTO;
import com.example.sfera_backend.entity.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public EventDTO eventToEventDTO(Event event) {
        return new EventDTO(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getFileUrl(),
                event.getDate(),
                event.getHour()
        );
    }
}
