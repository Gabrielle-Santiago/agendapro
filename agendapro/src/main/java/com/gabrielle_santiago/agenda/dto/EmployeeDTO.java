package com.gabrielle_santiago.agenda.dto;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDTO extends PeopleDTO {
    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private Long id;

    private String proof;

    public EmployeeDTO(String name,  String number, String cpf, LocalDate dateBrith, String email, String proof) {
        super(name, number, cpf, dateBrith, email);
        this.proof = proof;
    }

}
