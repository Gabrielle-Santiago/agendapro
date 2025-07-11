package com.gabrielle_santiago.agenda.calendar;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SchedulingRequestDTO {

    @NotBlank(message = "Title is required.")
    private String title;

    @NotNull(message = "Start date and time are required.")
    private LocalDateTime start;

    @NotNull(message = "End date and time are required.")
    private LocalDateTime end;

    @NotBlank(message = "Participant email is required.")
    @Email(message = "Invalid email format.")
    private String emailPatient;
}
