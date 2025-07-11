package com.gabrielle_santiago.agenda.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;

import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@DiscriminatorValue("consultations")
public class ConsultationEntity extends PeopleEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "patient_id")
    private Long patientId;

    private String title;
    @FutureOrPresent
    private LocalDateTime startConsultation;
    private LocalDateTime endConsultation;
    private String employeeId;

    public ConsultationEntity() {super();}

    public ConsultationEntity(Long patientId, String title, LocalDateTime startConsultation, LocalDateTime endConsultation, String employeeId) {
        this.patientId = patientId;
        this.title = title;
        this.startConsultation = startConsultation;
        this.endConsultation = endConsultation;
        this.employeeId = employeeId;
    }
}
