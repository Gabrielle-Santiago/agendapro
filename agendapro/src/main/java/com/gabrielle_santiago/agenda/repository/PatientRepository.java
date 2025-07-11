package com.gabrielle_santiago.agenda.repository;

import com.gabrielle_santiago.agenda.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    Optional<PatientEntity> findById(Long id);
}
