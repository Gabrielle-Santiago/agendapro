package com.gabrielle_santiago.agenda.dto;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDTO extends PeopleDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String proof;

    public EmployeeDTO(String name,  String number, String cpf, LocalDate dateBrith, String email, String proof) {
        super(name, number, cpf, dateBrith, email);
        this.proof = proof;
    }

}
