package com.gabrielle_santiago.agenda.entity;

import java.time.LocalDate;

import com.gabrielle_santiago.agenda.authentication.UserRole;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Setter
@DiscriminatorValue("patients")
public class PatientEntity extends PeopleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String describe;

    public String getDescribe() {
        return describe;
    }

    public PatientEntity(){
        super();
    }
     
    public PatientEntity(String name, String username, String passwd, String email, int contact_number, String cpf, LocalDate dateBirth, UserRole role, String describe) {
        super(name, username, passwd, email, contact_number, cpf, dateBirth, role);
        this.describe = describe;
    }
}
