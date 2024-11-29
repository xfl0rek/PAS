package pl.pas.aplikacjamvc.model;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@BsonDiscriminator("manager")
public class Manager extends User {

    public Manager() {
    }

    public Manager(String firstName, String lastName, String username, String email, String password) {
        super(firstName, lastName, username, email, password);
        this.setUserRole(UserRole.MANAGER);
        setActive(true);
    }
}
