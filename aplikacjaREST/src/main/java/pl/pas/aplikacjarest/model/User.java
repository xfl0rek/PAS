package pl.pas.aplikacjarest.model;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

//@Document(collection = "users")
@BsonDiscriminator("user")
public class User {
    @BsonId
    private long id;
    @BsonProperty("firstname")
    private String firstName;
    @BsonProperty("lastname")
    private String lastName;
    @BsonProperty("username")
    private String username;
    @BsonProperty("email")
    private String email;
    @BsonProperty("password")
    private String password;
    @BsonProperty("isActive")
    private boolean isActive = false;

    @BsonProperty("userRole")
    private UserRole userRole;

    @BsonCreator
    public User(@BsonId long id,
                @BsonProperty("firstname") String firstName,
                @BsonProperty("lastname") String lastName,
                @BsonProperty("username") String username,
                @BsonProperty("email") String email,
                @BsonProperty("password") String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return isActive;
    }

    public UserRole getUserRole() {
        return userRole;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}