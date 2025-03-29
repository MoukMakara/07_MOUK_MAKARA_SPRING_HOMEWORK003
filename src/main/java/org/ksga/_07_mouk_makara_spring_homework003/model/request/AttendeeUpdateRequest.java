package org.ksga._07_mouk_makara_spring_homework003.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendeeUpdateRequest {
    @NotBlank(message = "attendeeName must not be blank")
    private String attendeeName;
    @Email(regexp = "^[a-z0-9._%+-]+@gmail\\.com$")
    @NotBlank(message = "email must not be blank")
    @Schema(defaultValue = "example@gmail.com")
    private String email;
}
