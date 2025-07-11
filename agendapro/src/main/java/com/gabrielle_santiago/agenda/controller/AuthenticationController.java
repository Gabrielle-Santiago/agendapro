package com.gabrielle_santiago.agenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabrielle_santiago.agenda.dto.request.EmployeeRegisterDTO;
import com.gabrielle_santiago.agenda.dto.request.PatientRegisterDTO;
import com.gabrielle_santiago.agenda.dto.request.AuthenticationDTO;
import com.gabrielle_santiago.agenda.dto.request.LoginDTO;
import com.gabrielle_santiago.agenda.service.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("register")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private FormErrorsService formErrorsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        String token = authenticationService.authenticateUser(data);
        return ResponseEntity.ok(new LoginDTO(token));
    }

    @PostMapping("/patient")
    public ResponseEntity<?> registerPatient(@RequestBody @Valid PatientRegisterDTO dto) {
        formErrorsService.authenticatePasswd(dto.passwd());
        patientService.authenticateEmail(dto);
        patientService.authenticatePatientUsername(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Patient registered successfully.");
    }

    @PostMapping("/employee")
    public ResponseEntity<?> registerEmployee(@RequestBody @Valid EmployeeRegisterDTO dto) {
        formErrorsService.authenticatePasswd(dto.passwd());
        employeeService.authenticateEmail(dto);
        employeeService.authenticateEmployeeUsername(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Employee registered successfully.");
    }
}
