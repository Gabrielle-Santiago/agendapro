package com.gabrielle_santiago.agenda.dto.request;

import com.gabrielle_santiago.agenda.authentication.UserRole;

import java.time.LocalDateTime;

public record ConsultationDTO (
        String title,
        LocalDateTime startConsultation,
        LocalDateTime endConsultation,
        String resourceId,
        UserRole role
){}
