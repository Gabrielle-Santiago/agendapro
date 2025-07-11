package com.gabrielle_santiago.agenda.repository;

import com.gabrielle_santiago.agenda.dto.request.ConsultationSummaryDTO;
import com.gabrielle_santiago.agenda.entity.ConsultationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationRepository extends JpaRepository<ConsultationEntity, Long> {
    List<ConsultationEntity> findByEmployeeIdAndStartConsultationBetween(String employeeId, LocalDateTime startOfDay, LocalDateTime endOfDay);
    @Query("SELECT c FROM ConsultationEntity c WHERE c.employeeId = :employeeId " +
            "AND (" +
            "(c.startConsultation < :newSlotEnd AND c.endConsultation > :newSlotStart)" +
            ")")
    List<ConsultationEntity> findConflictingConsultations(@Param("employeeId") String employeeId,
                                                          @Param("newSlotStart") LocalDateTime newSlotStart,
                                                          @Param("newSlotEnd") LocalDateTime newSlotEnd);

    @Query("SELECT new com.gabrielle_santiago.agenda.dto.request.ConsultationSummaryDTO(c.title, c.startConsultation, c.endConsultation) FROM ConsultationEntity c")
    List<ConsultationSummaryDTO> findAllConsultations();
}
