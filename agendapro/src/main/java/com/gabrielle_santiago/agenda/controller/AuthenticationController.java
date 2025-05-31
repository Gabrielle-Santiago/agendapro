package com.gabrielle_santiago.agenda.controller;

import com.gabrielle_santiago.agenda.authentication.User;
import com.gabrielle_santiago.agenda.authentication.UserRole;
import com.gabrielle_santiago.agenda.dto.request.AuthenticationDTO;
import com.gabrielle_santiago.agenda.dto.request.LoginDTO;
import com.gabrielle_santiago.agenda.dto.request.RegisterDTO;
import com.gabrielle_santiago.agenda.entity.EmployeeEntity;
import com.gabrielle_santiago.agenda.entity.PatientEntity;
import com.gabrielle_santiago.agenda.entity.PeopleEntity;
import com.gabrielle_santiago.agenda.repository.UserRepository;
import com.gabrielle_santiago.agenda.service.TokenService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data, HttpServletResponse response) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.passwd());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        var user = (User) auth.getPrincipal();

        return ResponseEntity.ok(new LoginDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByLogin(data.username()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.passwd());

        PeopleEntity pessoa;
        if(data.role().equals("PATIENT")) {
            pessoa = new PatientEntity();
        } else {
            pessoa = new EmployeeEntity();
        }
        pessoa.setEmail(data.email());

        User newUser = new User(data.username(), encryptedPassword, UserRole.valueOf(data.role()));

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
