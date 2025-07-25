package com.example.sfera_backend.controller;

import com.example.sfera_backend.dto.request.EventRequest;
import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.EventDTO;
import com.example.sfera_backend.jwt.RequireToken;
import com.example.sfera_backend.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping
    @Operation(
            summary = "Barcha eventlarni listini kurish"
    )
    public ResponseEntity<ApiResponse<List<EventDTO>>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Bitta eventni kurish"
    )
    public ResponseEntity<ApiResponse<EventDTO>> getEventById(@PathVariable UUID id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }



    @PostMapping
    @Operation(
            summary = "Event saqlash uchun",
            security = @SecurityRequirement( name = "bearerAuth")
    )
    @RequireToken
    public ResponseEntity<ApiResponse<String>> createEvent(
            @RequestBody EventRequest eventRequest) {
        return ResponseEntity.ok(eventService.addEvent(eventRequest));
    }


    @PutMapping("/{id}")
    @Operation(
            summary = "Event tahrirlash uchun",
            security = @SecurityRequirement( name = "bearerAuth")
    )
    @RequireToken
    public ResponseEntity<ApiResponse<String>> updateEvent(@PathVariable UUID id,
                                                           @RequestBody EventRequest eventRequest){
        return ResponseEntity.ok(eventService.updateEvent(id,eventRequest));
    }



    @DeleteMapping("/{id}")
    @Operation(
            summary = "Event uchirish uchun",
            security = @SecurityRequirement( name = "bearerAuth")
    )
    @RequireToken
    public ResponseEntity<ApiResponse<String>> deleteEvent(@PathVariable UUID id) {
        return ResponseEntity.ok(eventService.deleteEvent(id));
    }
}
