package pl.pas.aplikacjarest.model;

public enum UserRole {
    ROLE_ADMIN, ROLE_MANAGER, ROLE_CLIENT;

    @Override
    public String toString() {
        return this.name();
    }
}
