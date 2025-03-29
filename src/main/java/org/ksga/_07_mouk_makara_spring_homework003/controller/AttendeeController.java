package org.ksga._07_mouk_makara_spring_homework003.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ksga._07_mouk_makara_spring_homework003.exception.VenueNotFoundException;
import org.ksga._07_mouk_makara_spring_homework003.model.Attendee;
import org.ksga._07_mouk_makara_spring_homework003.model.request.AttendeeCreateRequest;
import org.ksga._07_mouk_makara_spring_homework003.model.request.AttendeeUpdateRequest;
import org.ksga._07_mouk_makara_spring_homework003.model.response.ApiResponse;
import org.ksga._07_mouk_makara_spring_homework003.service.AttendeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/attendees")
@RequiredArgsConstructor
public class AttendeeController {
    private final AttendeeService attendeeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Attendee>>> findAllAttendees(
            @RequestParam(defaultValue = "1") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit) {

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
        ApiResponse<List<Attendee>> response = ApiResponse.<List<Attendee>>builder()
                .message("The attendee has been successfully founded.")
                .payload(attendeeService.findAllAttendees(offset, limit))
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Attendee>> findAttendeeById(@PathVariable Integer id) {
        Attendee attendee = attendeeService.findAttendeeById(id);
        if (attendee == null) {
            throw new VenueNotFoundException("The attendee id " + id + " has not been found.");
        }
        ApiResponse<Attendee> response = ApiResponse.<Attendee>builder()
                .message("The attendee has been successfully founded.")
                .payload(attendee)
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<?> createAttendee(@RequestBody @Valid AttendeeCreateRequest attendeeCreateRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>().builder()
                        .message("The attendee has been successfully added.")
                        .payload(attendeeService.createAttendee(attendeeCreateRequest))
                        .status(HttpStatus.CREATED)
                        .timestamp(LocalDateTime.now())
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAttendeeById(@PathVariable Integer id) {
        Attendee attendee = attendeeService.findAttendeeById(id);
        if (attendee == null) {
            throw new VenueNotFoundException("The attendee id " + id + " has not been found.");
        }
        attendeeService.deleteAttendeeById(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>().builder()
                        .message("The attendee has been successfully deleted.")
                        .status(HttpStatus.OK)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Attendee>> updateAttendeeById(@PathVariable Integer id, @RequestBody @Valid AttendeeUpdateRequest attendeeUpdateRequest){
        Attendee attendee = attendeeService.findAttendeeById(id);
        if (attendee == null) {
            throw new VenueNotFoundException("The attendee id " + id + " has not been found.");
        }
        Attendee attendUpdate = attendeeService.updateAttendeeById(id, attendeeUpdateRequest);

        ApiResponse<Attendee> response = ApiResponse.<Attendee>builder()
                .message("The attendee has been successfully updated.")
                .payload(attendUpdate)
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
