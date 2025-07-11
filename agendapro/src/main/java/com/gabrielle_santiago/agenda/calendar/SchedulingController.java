package com.gabrielle_santiago.agenda.calendar;

import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.validation.Valid;

import java.io.IOException;

@RestController
@RequestMapping("/calendar")
public class SchedulingController {

    @Autowired
    private GoogleCalendarAuthConfig googleCalendarAuthConfig;

    @Autowired
    private SchedulingService service;

    @GetMapping("/authorize")
    public RedirectView authorizeGoogleCalendar() {
        try {
            googleCalendarAuthConfig.getCredentialForUser("PROFESSIONAL_USER");
            return new RedirectView("/calendar/auth-success");
        } catch (IOException e) {
            e.printStackTrace();
            return new RedirectView("/calendar/auth-error?message=" + e.getMessage());
        }
    }

    @GetMapping("/auth-success")
    public ResponseEntity<String> authSuccess() {
        return ResponseEntity.ok("Google Calendar authorization successful!");
    }

    @GetMapping("/auth-error")
    public ResponseEntity<String> authError(@RequestParam(required = false) String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error during authorization: " + (message != null ? message : "Check the server logs."));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createScheduling(@Valid @RequestBody SchedulingRequestDTO requestDTO) {
        try {
            Event createdEvent = service.scheduleNewEvent(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Appointment created! Link: " + createdEvent.getHtmlLink());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error scheduling: " + e.getMessage());
        }
    }
}