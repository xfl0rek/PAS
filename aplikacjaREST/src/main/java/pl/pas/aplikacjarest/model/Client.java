package pl.pas.aplikacjarest.model;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@BsonDiscriminator("client")
public class Client extends User {

    public Client (String firstName, String lastName, String username, String email, String password) {
        super(firstName, lastName, username, email, password);
        this.setUserRole(UserRole.ROLE_CLIENT);
        setActive(true);
    }

    public Client() {
    }
}
