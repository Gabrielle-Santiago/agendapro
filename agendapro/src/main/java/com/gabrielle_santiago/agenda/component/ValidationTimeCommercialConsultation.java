package com.gabrielle_santiago.agenda.component;

import com.gabrielle_santiago.agenda.dto.request.ConsultationDTO;
import com.gabrielle_santiago.agenda.exceptions.ConsultationTimeException;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class ValidationTimeCommercialConsultation {
    private static final LocalTime BUSINESS_START_TIME = LocalTime.of(8, 0);
    private static final LocalTime BUSINESS_END_TIME = LocalTime.of(18, 0);

    public void valid(ConsultationDTO consultationDTO){
        LocalDateTime consultationStartTime = consultationDTO.startConsultation();
        LocalDateTime consultationEndTime = consultationDTO.endConsultation();

        DayOfWeek dayOfWeek = consultationStartTime.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new ConsultationTimeException("Appointments cannot be scheduled on weekends!");
        }
        if(consultationStartTime.toLocalTime().isBefore(BUSINESS_START_TIME)){
            throw new ConsultationTimeException("The consultation cannot start before the clinic opening time. Try starting at 8:00 am.");
        }
        if(consultationEndTime.toLocalTime().isAfter(BUSINESS_END_TIME)){
            throw new ConsultationTimeException("Appointments cannot be scheduled after opening hours.");
        }
    }
}
