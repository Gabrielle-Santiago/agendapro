package com.gabrielle_santiago.agenda.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
public class PeopleDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int number;
    private String cpf;
    private Date dateBrith;
    private String email;

    public PeopleDTO(String name, int number, String cpf, Date dateBrith, String email) {
        this.name = name;
        this.number = number;
        this.cpf = cpf;
        this.dateBrith = dateBrith;
        this.email = email;
    }

}
