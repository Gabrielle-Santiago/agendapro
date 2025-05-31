package com.gabrielle_santiago.agenda.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@DiscriminatorValue("people")
public abstract class PeopleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;
    private int number;
    private String cpf;
    private Date dateBrith;
    private String email;

    public PeopleEntity(){}

    public PeopleEntity(String name, int number, String cpf, Date dateBrith, String email) {
        this.name = name;
        this.number = number;
        this.cpf = cpf;
        this.dateBrith = dateBrith;
        this.email = email;
    }
}
