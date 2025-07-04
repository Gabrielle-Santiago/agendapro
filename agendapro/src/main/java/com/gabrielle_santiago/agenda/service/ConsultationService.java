package com.gabrielle_santiago.agenda.service;

import com.gabrielle_santiago.agenda.component.ValidationTimeCommercialConsultation;
import com.gabrielle_santiago.agenda.dto.request.ConsultationDTO;
import com.gabrielle_santiago.agenda.entity.ConsultationEntity;
import com.gabrielle_santiago.agenda.exceptions.ConsultationTimeException;
import com.gabrielle_santiago.agenda.mapper.ConsultationMapper;
import com.gabrielle_santiago.agenda.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultationService {
    private final ConsultationRepository consultationRepository;
    private final ValidationTimeCommercialConsultation validationTime;
    private static final LocalTime BUSINESS_START_TIME = LocalTime.of(8, 0);
    private static final LocalTime BUSINESS_END_TIME = LocalTime.of(18, 0);
    private static final int CONSULTATION_DURATION_MINUTES = 40;
    private static final int BREAK_TIME_MINUTES = 20;

    @Autowired
    private ConsultationMapper consultationMapper;

    @Autowired
    public ConsultationService(ConsultationRepository consultationRepository, ValidationTimeCommercialConsultation validationTime){
        this.consultationRepository = consultationRepository;
        this.validationTime = validationTime;
    }

    public List<LocalDateTime> getAvailableSlots(LocalDate date, String resourceId){
        if(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY){
            return new ArrayList<>();
        }

        List<LocalDateTime> allPossibleSlots = generateAllPossibleSlots(date);
        if(allPossibleSlots.isEmpty()){
            return new ArrayList<>();
        }

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        List<ConsultationEntity> existingAppointments =
                consultationRepository.findByResourceIdAndStartConsultationBetween(resourceId, startOfDay, endOfDay);

        List<LocalDateTime> availableSlots = new ArrayList<>();
        for (LocalDateTime slotStart : allPossibleSlots) {
            LocalDateTime slotEnd = slotStart.plusMinutes(CONSULTATION_DURATION_MINUTES);
            boolean isAvailable = true;
            for (ConsultationEntity existing : existingAppointments) {
                if (slotStart.isBefore(existing.getEndConsultation()) && slotEnd.isAfter(existing.getStartConsultation())) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                availableSlots.add(slotStart);
            }
        }
        return availableSlots;
    }

    private List<LocalDateTime> generateAllPossibleSlots(LocalDate date) {
        List<LocalDateTime> slots = new ArrayList<>();
        LocalDateTime currentSlotStart = date.atTime(BUSINESS_START_TIME);
        LocalDateTime businessEndDateTime = date.atTime(BUSINESS_END_TIME);

        while (currentSlotStart.isBefore(businessEndDateTime)) {
            LocalDateTime slotEnd = currentSlotStart.plusMinutes(CONSULTATION_DURATION_MINUTES);
            if (!slotEnd.isAfter(businessEndDateTime)) {
                slots.add(currentSlotStart);
            } else {
                break;
            }
            currentSlotStart = slotEnd.plusMinutes(BREAK_TIME_MINUTES);
        }
        return slots;
    }

    public void create(ConsultationDTO consultationDTO){
        validationTime.valid(consultationDTO);

        LocalDateTime slotStart = consultationDTO.startConsultation();
        LocalDateTime slotEnd = consultationDTO.endConsultation();
        String resourceId = consultationDTO.resourceId();

        List<ConsultationEntity> conflitConsultation =
                consultationRepository.findConflictingConsultations(resourceId, slotStart, slotEnd);

        if(!conflitConsultation.isEmpty()){
            throw new ConsultationTimeException("The selected time is no longer available. Please try again!");
        }

        ConsultationEntity entity = consultationMapper.toEntity(consultationDTO);
        consultationRepository.save(entity);
    }
}
