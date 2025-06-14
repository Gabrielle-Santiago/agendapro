package com.gabrielle_santiago.agenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabrielle_santiago.agenda.dto.request.EmployeeRegisterDTO;
import com.gabrielle_santiago.agenda.dto.request.PatientRegisterDTO;
import com.gabrielle_santiago.agenda.dto.request.AuthenticationDTO;
import com.gabrielle_santiago.agenda.dto.request.LoginDTO;
import com.gabrielle_santiago.agenda.entity.EmployeeEntity;
import com.gabrielle_santiago.agenda.entity.PatientEntity;
import com.gabrielle_santiago.agenda.entity.PeopleEntity;
import com.gabrielle_santiago.agenda.mapper.EmployeeMapper;
import com.gabrielle_santiago.agenda.mapper.PatientMapper;
import com.gabrielle_santiago.agenda.repository.UserRepository;
import com.gabrielle_santiago.agenda.service.TokenService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("register")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data, HttpServletResponse response) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.passwd());

        try {
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((PeopleEntity) auth.getPrincipal());    
            return ResponseEntity.ok(new LoginDTO(token));

        } catch (Exception e) {
            System.out.println("Authentication error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid credentials or authentication error.");
        }
    }

    @PostMapping("/patient")
    public ResponseEntity<?> registerPatient(@RequestBody @Valid PatientRegisterDTO dto) {

        if (repository.findByUsername(dto.username()) != null) {
            return ResponseEntity.badRequest().body("Username already exists.");
        }

        PatientEntity entity = patientMapper.toEntity(dto);
        repository.save(entity);

        return ResponseEntity.status(HttpStatus.CREATED).body("Patient registered successfully.");
    }

    @PostMapping("/employee")
    public ResponseEntity<?> registerEmployee(@RequestBody @Valid EmployeeRegisterDTO dto) {

        if (repository.findByUsername(dto.username()) != null) {
            return ResponseEntity.badRequest().body("Username already exists.");
        }

        EmployeeEntity entity = employeeMapper.toEntity(dto);
        repository.save(entity);

        return ResponseEntity.status(HttpStatus.CREATED).body("Employee registered successfully.");
    }
}
