package pl.pas.aplikacjarest.model;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@BsonDiscriminator("client")
public class Client extends User {
    private ClientType type;

    public Client(long id, String firstName, String lastName, String username, String email, String password, ClientType type) {
        super(id, firstName, lastName, username, email, password);
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
