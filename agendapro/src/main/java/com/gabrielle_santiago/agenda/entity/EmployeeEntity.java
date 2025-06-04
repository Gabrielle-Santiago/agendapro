package com.gabrielle_santiago.agenda.entity;

import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDate;

import com.gabrielle_santiago.agenda.authentication.UserRole;

@Entity
@Setter
@DiscriminatorValue("employees")
public class EmployeeEntity extends PeopleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String proof;
    
    public String getProof() {
        return proof;
    }

    public EmployeeEntity(){
        super();
    }
    
    public EmployeeEntity(String name, String username, String passwd, String email, int contact_number, String cpf, LocalDate dateBirth, UserRole role, String proof) {
        super(name, username, passwd, email, contact_number, cpf, dateBirth, role);
        this.proof = proof;
    }

}
