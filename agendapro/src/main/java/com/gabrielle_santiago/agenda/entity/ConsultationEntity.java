package com.gabrielle_santiago.agenda.entity;

import com.gabrielle_santiago.agenda.authentication.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@DiscriminatorValue("consultations")
public class ConsultationEntity extends PeopleEntity{
    private String title;
    @FutureOrPresent
    private LocalDateTime startConsultation;
    private LocalDateTime endConsultation;
    private String resourceId;
    private UserRole role;

    public ConsultationEntity() {super();}

    public ConsultationEntity(String name, String username, String passwd, String email, String contact_number, String cpf, LocalDate dateBirth, UserRole role, String title, LocalDateTime startConsultation, LocalDateTime endConsultation, String resourceId) {
        super(name, username, passwd, email, contact_number, cpf, dateBirth, role);
        this.title = title;
        this.startConsultation = startConsultation;
        this.endConsultation = endConsultation;
        this.resourceId = resourceId;
    }
}
