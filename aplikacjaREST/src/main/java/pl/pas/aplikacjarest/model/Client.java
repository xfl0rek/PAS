package pl.pas.aplikacjarest.model;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator("client")
public class Client extends User {
    @BsonProperty("clientType")
    private ClientType type;

    public Client(String firstName, String lastName, String username, String email, String password, @BsonProperty("clientType") ClientType type) {
        super(firstName, lastName, username, email, password);
        this.setUserRole(UserRole.CLIENT);
        this.type = type;
        setActive(true);
    }

    public Client() {
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    public double applyDiscount(double price) {
        if (this.type == ClientType.Premium) {
            return price * 0.85;
        }
        return price;
    }
}
