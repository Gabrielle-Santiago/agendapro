package com.gabrielle_santiago.agenda.dto;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientDTO extends PeopleDTO {
    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private Long id;

    private String describe;

    public PatientDTO(String name, String number, String cpf, LocalDate dateBrith, String email, String describe) {
        super(name, number, cpf, dateBrith, email);
        this.describe = describe;
    }
}
