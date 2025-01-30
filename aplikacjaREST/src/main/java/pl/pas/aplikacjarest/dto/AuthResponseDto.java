package pl.pas.aplikacjarest.dto;

import pl.pas.aplikacjarest.model.UserRole;

public class AuthResponseDto {
    private String sub;
    private UserRole role;
    
    public AuthResponseDto(String sub, UserRole role) {
        this.sub = sub;
        this.role = role;
    }
    
    public String getSub() {
        return sub;
    }
    
    public void setSub(String sub) {
        this.sub = sub;
    }
    
    public UserRole getRole() {
        return role;
    }
    
    public void setRole(UserRole role) {
        this.role = role;
    }
}
