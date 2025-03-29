package org.ksga._07_mouk_makara_spring_homework003.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ksga._07_mouk_makara_spring_homework003.exception.VenueNotFoundException;
import org.ksga._07_mouk_makara_spring_homework003.model.Event;
import org.ksga._07_mouk_makara_spring_homework003.model.request.EventCreateRequest;
import org.ksga._07_mouk_makara_spring_homework003.model.request.EventUpdateRequest;
import org.ksga._07_mouk_makara_spring_homework003.model.response.ApiResponse;
import org.ksga._07_mouk_makara_spring_homework003.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Event>>> findAllEvents(
            @RequestParam(defaultValue = "1") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit
    ) {
        Map<String, String> errors = new HashMap<>();

        if (offset < 1) {
            errors.put("offset", "must be greater than 0");
        }
        if (limit < 1 ) {
            errors.put("limit", "must be greater than 0");
        }
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString());
        }

        ApiResponse<List<Event>> response = ApiResponse.<List<Event>>builder()
                .message("All events have been successfully fetched.")
                .payload(eventService.findAllEvents(offset, limit))
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Event>> findEventById(@PathVariable Integer id) {
        Event event = eventService.findEventById(id);

        if (event == null) {
            throw new VenueNotFoundException("The event id " + id + " has not been found.");
        }

        ApiResponse<Event> response = ApiResponse.<Event>builder()
                    .message("Find event successfully fetched.")
                    .payload(event)
                    .status(HttpStatus.OK)
                    .timestamp(LocalDateTime.now())
                    .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping
    public ResponseEntity<ApiResponse<Event>> createEvent(@RequestBody @Valid EventCreateRequest eventCreateRequest) {
        Event newEvent = eventService.createEvent(eventCreateRequest);

        ApiResponse<Event> response = ApiResponse.<Event>builder()
                .message("Event successfully created.")
                .payload(newEvent)
                .status(HttpStatus.CREATED)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEventById(@PathVariable Integer id) {
        Event event = eventService.findEventById(id);

        if (event == null) {
            throw new VenueNotFoundException("The event id " + id + " has not been found.");
        }
        eventService.deleteEventById(id);
        return ResponseEntity.ok(ApiResponse.<Event>builder()
                        .message("Delete event successfully.")
                        .status(HttpStatus.OK)
                        .timestamp(LocalDateTime.now())
                .build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEventById(@PathVariable Integer id, @RequestBody @Valid EventUpdateRequest eventUpdateRequest) {
        Event event = eventService.findEventById(id);
        if (event == null) {
            throw new VenueNotFoundException("The event id " + id + " has not been found.");
        }
        Event updateEvent = eventService.updateEventById(id, eventUpdateRequest);
        return ResponseEntity.ok(ApiResponse.<Event>builder()
                        .message("Update event successfully.")
                        .payload(updateEvent)
                        .status(HttpStatus.OK)
                        .timestamp(LocalDateTime.now())
                .build());
    }
}
