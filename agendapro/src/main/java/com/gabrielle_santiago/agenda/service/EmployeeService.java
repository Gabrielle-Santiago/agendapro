package com.gabrielle_santiago.agenda.service;

import com.gabrielle_santiago.agenda.dto.request.EmployeeRegisterDTO;
import com.gabrielle_santiago.agenda.entity.EmployeeEntity;
import com.gabrielle_santiago.agenda.exceptions.EmailExistsException;
import com.gabrielle_santiago.agenda.exceptions.ExistingUsernameException;
import com.gabrielle_santiago.agenda.exceptions.InvalidPasswdException;
import com.gabrielle_santiago.agenda.mapper.EmployeeMapper;
import com.gabrielle_santiago.agenda.repository.UserRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private EmployeeMapper employeeMapper;

    public void authenticateEmployeeUsername(EmployeeRegisterDTO dto){
        if (repository.findByUsername(dto.username()) != null) {
            throw new ExistingUsernameException("Existing user. Try another!!");
        }

        EmployeeEntity entity = employeeMapper.toEntity(dto);
        repository.save(entity);
    }

    public void authenticateEmail(@Valid EmployeeRegisterDTO dto){
        if (repository.findByEmail(dto.email()).isPresent()) {
            throw new EmailExistsException("This email is already in use. Try another!!");
        }
    }
}
