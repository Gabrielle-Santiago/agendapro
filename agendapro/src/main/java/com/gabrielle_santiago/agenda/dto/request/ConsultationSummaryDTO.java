package com.gabrielle_santiago.agenda.dto.request;

import java.time.LocalDateTime;

public record ConsultationSummaryDTO (
        String title,
        LocalDateTime startConsultation,
        LocalDateTime endConsultation
) {}
