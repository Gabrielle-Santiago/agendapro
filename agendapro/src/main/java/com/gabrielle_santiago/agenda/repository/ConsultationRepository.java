package com.gabrielle_santiago.agenda.repository;

import com.gabrielle_santiago.agenda.authentication.UserRole;
import com.gabrielle_santiago.agenda.entity.ConsultationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationRepository extends JpaRepository<ConsultationEntity, Long> {
    List<ConsultationEntity> findByResourceIdAndStartConsultationBetween(String resourceId, LocalDateTime startOfDay, LocalDateTime endOfDay);
    @Query("SELECT c FROM ConsultationEntity c WHERE c.resourceId = :resourceId " +
            "AND (" +
            "(c.startConsultation < :newSlotEnd AND c.endConsultation > :newSlotStart)" +
            ")")
    List<ConsultationEntity> findConflictingConsultations(@Param("resourceId") String resourceId,
                                                          @Param("newSlotStart") LocalDateTime newSlotStart,
                                                          @Param("newSlotEnd") LocalDateTime newSlotEnd);
}
