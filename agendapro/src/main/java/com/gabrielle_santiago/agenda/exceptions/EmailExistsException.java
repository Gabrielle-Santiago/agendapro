package com.gabrielle_santiago.agenda.exceptions;

public class EmailExistsException extends RuntimeException{
    public EmailExistsException(String message){
        super(message);
    }
}
