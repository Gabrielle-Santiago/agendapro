package com.gabrielle_santiago.agenda.authentication;

public enum UserRole {
    ROLE_EMPLOYEE("roleEmployee"),
    ROLE_PATIENT("rolePatient");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }

    public static UserRole fromString(String roleStr){
        for (UserRole role: UserRole.values()){
            if(role.getRole().equalsIgnoreCase(roleStr)){
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role: " +  roleStr);
    }
}
