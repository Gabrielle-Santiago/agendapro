package com.gabrielle_santiago.agenda.service;

import com.gabrielle_santiago.agenda.dto.request.AuthenticationDTO;
import com.gabrielle_santiago.agenda.entity.PeopleEntity;
import com.gabrielle_santiago.agenda.exceptions.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public String authenticateUser(AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.passwd());

        try {
            var auth = authenticationManager.authenticate(usernamePassword);
            return tokenService.generateToken((PeopleEntity)auth.getPrincipal());
        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException("Invalid credentials. Incorrect username or password.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to authenticate user: " + e.getMessage());
        }
    }
}