package pl.pas.aplikacjarest.model;

public enum UserRole {
    ADMIN, MANAGER, CLIENT;

    @Override
    public String toString() {
        return "ROLE_" + this.name();
    }
}
