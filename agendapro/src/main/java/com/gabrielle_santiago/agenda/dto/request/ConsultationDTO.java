package com.gabrielle_santiago.agenda.dto.request;

import java.time.LocalDateTime;

public record ConsultationDTO (
        Long patientId,
        String title,
        LocalDateTime startConsultation,
        LocalDateTime endConsultation,
        String employeeId
){}
