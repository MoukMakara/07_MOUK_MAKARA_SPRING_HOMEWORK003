package org.ksga._07_mouk_makara_spring_homework003.model.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenueUpdateRequest {
    @NotBlank(message = "venueName must not be blank")
    private String venueName;
    @NotBlank(message = "location must not be blank")
    private String location;
}
