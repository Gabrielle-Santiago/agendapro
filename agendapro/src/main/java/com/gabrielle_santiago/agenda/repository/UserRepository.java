package com.gabrielle_santiago.agenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabrielle_santiago.agenda.entity.PeopleEntity;

public interface UserRepository extends JpaRepository<PeopleEntity, String> {
    PeopleEntity findByUsername(String username);
}
