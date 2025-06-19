package com.gabrielle_santiago.agenda.service;

import com.gabrielle_santiago.agenda.exceptions.InvalidPasswdException;
import com.gabrielle_santiago.agenda.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormErrorsService {
    @Autowired
    private UserRepository repository;

    public void authenticatePasswd(String passwd){
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?])(?=\\S+$).{8,}$";

        if(!passwd.matches(passwordRegex)){
            throw new InvalidPasswdException("The password must have at least 8 characters," +
                    " one symbol, " +
                    "one uppercase letter " +
                    "and one lowercase letter.");
        }
    }
}
