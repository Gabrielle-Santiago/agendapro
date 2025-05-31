package com.gabrielle_santiago.agenda.authentication;

public enum UserRole {
    EMPLOYEE("employee"),
    PATIENT("patient");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
