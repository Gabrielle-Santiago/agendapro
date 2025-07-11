package com.gabrielle_santiago.agenda.service;

import com.gabrielle_santiago.agenda.component.ValidationTimeCommercialConsultation;
import com.gabrielle_santiago.agenda.dto.request.ConsultationDTO;
import com.gabrielle_santiago.agenda.dto.request.ConsultationSummaryDTO;
import com.gabrielle_santiago.agenda.entity.*;
import com.gabrielle_santiago.agenda.exceptions.ConsultationTimeException;
import com.gabrielle_santiago.agenda.exceptions.PatientNotFoundException;
import com.gabrielle_santiago.agenda.mapper.ConsultationMapper;
import com.gabrielle_santiago.agenda.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultationService {
    private final ConsultationRepository consultationRepository;
    private final ValidationTimeCommercialConsultation validationTime;
    private static final LocalTime bussinesStartTime = LocalTime.of(8, 0);
    private static final LocalTime bussinesEndTime = LocalTime.of(18, 0);
    private static final int consultationDurationMinutes = 40;
    private static final int breakTimeMinutes = 20;

    @Autowired
    private ConsultationMapper consultationMapper;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    public ConsultationService(ConsultationRepository consultationRepository,
                               ValidationTimeCommercialConsultation validationTime,
                               ConsultationMapper consultationMapper,
                               PatientRepository patientRepository) {
        this.consultationRepository = consultationRepository;
        this.validationTime = validationTime;
        this.consultationMapper = consultationMapper;
        this.patientRepository = patientRepository;
    }

    public List<LocalDateTime> getAvailableSlots(LocalDate date, String employeeId){
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
                consultationRepository.findByEmployeeIdAndStartConsultationBetween(employeeId, startOfDay, endOfDay);

        List<LocalDateTime> availableSlots = new ArrayList<>();
        for (LocalDateTime slotStart : allPossibleSlots) {
            LocalDateTime slotEnd = slotStart.plusMinutes(consultationDurationMinutes);
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
        LocalDateTime currentSlotStart = date.atTime(bussinesStartTime);
        LocalDateTime businessEndDateTime = date.atTime(bussinesEndTime);

        while (currentSlotStart.isBefore(businessEndDateTime)) {
            LocalDateTime slotEnd = currentSlotStart.plusMinutes(consultationDurationMinutes);
            if (!slotEnd.isAfter(businessEndDateTime)) {
                slots.add(currentSlotStart);
            } else {
                break;
            }
            currentSlotStart = slotEnd.plusMinutes(breakTimeMinutes);
        }
        return slots;
    }

    @Transactional
    public ConsultationEntity create(ConsultationDTO dto){
        createHoursConsultation(dto);
        getLoggedInUserId();
        validatePatientExists(dto.patientId());

        ConsultationEntity entity = consultationMapper.toEntity(dto);
        return consultationRepository.save(entity);
    }

    public void createHoursConsultation(ConsultationDTO dto) {
        validationTime.valid(dto);

        LocalDateTime slotStart = dto.startConsultation();
        LocalDateTime slotEnd = dto.endConsultation();
        String employeeId = dto.employeeId();

        List<ConsultationEntity> conflitConsultation =
                consultationRepository.findConflictingConsultations(employeeId, slotStart, slotEnd);

        if (!conflitConsultation.isEmpty()) {
            throw new ConsultationTimeException("The selected time is no longer available. Please try again!");
        }
    }

    private Long getLoggedInUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof PeopleEntity){
            PeopleEntity loggedInUser = (PeopleEntity) authentication.getPrincipal();
            return loggedInUser.getId();
        }
        return null;
    }

    private PatientEntity validatePatientExists(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient with ID " + patientId + " not found."));
    }

    public List<ConsultationSummaryDTO> getAllConsultations() {
        return consultationRepository.findAllConsultations();
    }
}
