package org.ksga._07_mouk_makara_spring_homework003.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ksga._07_mouk_makara_spring_homework003.model.Attendee;
import org.ksga._07_mouk_makara_spring_homework003.model.Venue;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventCreateRequest {
    @NotBlank(message = "eventName must not be blank")
    private String eventName;
    @NotNull(message = "eventDate must not be null")
    private LocalDateTime eventDate;
    private Integer venue;
    private List<Integer> attendees;
}
