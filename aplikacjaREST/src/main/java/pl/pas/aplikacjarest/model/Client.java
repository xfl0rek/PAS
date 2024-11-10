package pl.pas.aplikacjarest.model;


public class Client extends User {
    private ClientType type;

    public Client(String firstName, String lastName, String username, String email, String password, ClientType type) {
        super(firstName, lastName, username, email, password);
        this.setUserRole(UserRole.CLIENT);
        this.type = type;
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
