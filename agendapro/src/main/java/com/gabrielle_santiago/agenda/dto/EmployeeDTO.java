package com.gabrielle_santiago.agenda.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

import java.util.Date;

public class EmployeeDTO extends PeopleDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;
    private String proof;
    private String username;
    private String passwd;

    public EmployeeDTO(String name, int number, String cpf, Date dateBrith, String email, String role, String proof) {
        super(name, number, cpf, dateBrith, email);
        this.role = role;
        this.proof = proof;
    }

}
