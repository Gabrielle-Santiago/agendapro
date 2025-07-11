package com.gabrielle_santiago.agenda.controller;

import com.gabrielle_santiago.agenda.dto.request.ConsultationDTO;
import com.gabrielle_santiago.agenda.dto.request.ConsultationSummaryDTO;
import com.gabrielle_santiago.agenda.entity.ConsultationEntity;
import com.gabrielle_santiago.agenda.service.ConsultationService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {

    private final ConsultationService service;

    @Autowired
    public ConsultationController(ConsultationService service) {
        this.service = service;
    }

    @GetMapping("/available-slots")
    public ResponseEntity<List<String>> getAvailableSlots(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam String employeeId) {
        try {
            List<LocalDateTime> availableSlots = service.getAvailableSlots(date, employeeId);
            List<String> formattedSlots = availableSlots.stream()
                    .map(slot -> slot.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(formattedSlots);
        } catch (Exception e) {
            System.err.println("Error fetching available slots: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of("Error loading available slots. Please try again later."));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createConsultation(@RequestBody @Valid ConsultationDTO dto) {
        service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Consultation created successfully!");
    }

    @GetMapping("/allConsultations")
    public List<ConsultationSummaryDTO> allConsultations(){
        return service.getAllConsultations();
    }
}
