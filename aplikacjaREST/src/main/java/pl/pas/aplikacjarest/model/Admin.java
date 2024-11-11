package pl.pas.aplikacjarest.model;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@BsonDiscriminator("admin")
public class Admin extends User {

    public Admin() {
    }

    public Admin(String firstName, String lastName, String username, String email, String password) {
        super(firstName, lastName, username, email, password);
        this.setUserRole(UserRole.ADMIN);
        setActive(true);
    }
}
