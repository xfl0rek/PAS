package pl.pas.aplikacjarest.model;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@BsonDiscriminator("manager")
public class Manager extends User {

    public Manager() {
    }

    public Manager(long id, String firstName, String lastName, String username, String email, String password) {
        super(id, firstName, lastName, username, email, password);
        this.setUserRole(UserRole.MANAGER);
        setActive(true);
    }
}
