package com.gabrielle_santiago.agenda.exceptions;

public class ExistingUsernameException extends RuntimeException {
    public ExistingUsernameException(String message){
        super(message);
    }
}
