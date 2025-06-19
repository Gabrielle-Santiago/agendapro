package com.gabrielle_santiago.agenda.service;

import com.gabrielle_santiago.agenda.dto.request.PatientRegisterDTO;
import com.gabrielle_santiago.agenda.entity.PatientEntity;
import com.gabrielle_santiago.agenda.exceptions.EmailExistsException;
import com.gabrielle_santiago.agenda.exceptions.ExistingUsernameException;
import com.gabrielle_santiago.agenda.mapper.PatientMapper;
import com.gabrielle_santiago.agenda.repository.UserRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PatientMapper patientMapper;

    public void authenticatePatientUsername(PatientRegisterDTO dto){
        if (repository.findByUsername(dto.username()) != null) {
            throw new ExistingUsernameException("Existing user. Try another!!");
        }

        PatientEntity entity = patientMapper.toEntity(dto);
        repository.save(entity);
    }

    public void authenticateEmail(@Valid PatientRegisterDTO dto){
        if (repository.findByEmail(dto.email()).isPresent()) {
            throw new EmailExistsException("This email is already in use. Try another!!");
        }
    }
}
