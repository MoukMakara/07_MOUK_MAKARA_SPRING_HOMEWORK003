package org.ksga._07_mouk_makara_spring_homework003.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ksga._07_mouk_makara_spring_homework003.exception.VenueNotFoundException;
import org.ksga._07_mouk_makara_spring_homework003.model.Venue;
import org.ksga._07_mouk_makara_spring_homework003.model.request.VenueCreateRequest;
import org.ksga._07_mouk_makara_spring_homework003.model.request.VenueUpdateRequest;
import org.ksga._07_mouk_makara_spring_homework003.model.response.ApiResponse;
import org.ksga._07_mouk_makara_spring_homework003.service.VenueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/venues")
@RequiredArgsConstructor
public class VenueController {
    private final VenueService venueService;
    // findAllVenues
    @GetMapping
    public ResponseEntity<ApiResponse<List<Venue>>> findAllVenues(
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

        ApiResponse<List<Venue>> response = ApiResponse.<List<Venue>>builder()
                .message("Find all venues successfully")
                .payload(venueService.findAllVenues(offset, limit))
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createVenue(@RequestBody @Valid VenueCreateRequest venueCreateRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.builder()
                        .message("Create venue is successfully")
                        .payload(venueService.createVenue(venueCreateRequest))
                        .status(HttpStatus.OK)
                        .timestamp(LocalDateTime.now())
                .build());
    }
    // findVenueById
    @GetMapping("/{id}")
    public ResponseEntity<?> findVenueById(@PathVariable @Valid Integer id) {
        Venue venue = venueService.findVenueById(id);

        if (venue == null) {
            throw new VenueNotFoundException("The venue id " + id + " has not been found.");
        }

        return ResponseEntity.ok(ApiResponse.<Venue>builder()
                .message("Find venue by id is successful")
                .payload(venue)
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build());
    }
    // deleteInstructorById
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVenueById(@PathVariable Integer id){
        Venue venue = venueService.findVenueById(id);

        if (venue == null) {
            throw new VenueNotFoundException("The venue id " + id + " has not been found.");
        }

        venueService.deleteVenueById(id);

        return ResponseEntity.ok(ApiResponse.builder()
                           .message("Delete instructor by id is successfully")
                           .status(HttpStatus.NO_CONTENT)
                           .timestamp(LocalDateTime.now())
                           .build());
    }
    // updateInstructorById
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVenueById(@PathVariable Integer id, @RequestBody @Valid VenueUpdateRequest venueUpdateRequest) {

        Venue venue = venueService.findVenueById(id);

        if (venue == null) {
            throw new VenueNotFoundException("The venue id " + id + " has not been found.");
        }

        Venue updatedInstructor = venueService.updateVenueById(id, venueUpdateRequest);

        ApiResponse<Venue> response = ApiResponse.<Venue>builder()
                .message("The instructor has been successfully updated.")
                .payload(updatedInstructor)
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
