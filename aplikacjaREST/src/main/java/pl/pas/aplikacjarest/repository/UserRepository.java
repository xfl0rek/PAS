package pl.pas.aplikacjarest.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.springframework.stereotype.Repository;
import pl.pas.aplikacjarest.model.ClientType;
import pl.pas.aplikacjarest.model.User;
import pl.pas.aplikacjarest.model.UserRole;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository extends AbstractMongoRepository {
    @Override
    public void close() throws Exception {

    }

    public UserRepository() {
        this.initDBConnection();
    }

    public User findByUsername(String username) {
        MongoCollection<User> collection = getDatabase().getCollection("users", User.class);
        return collection.find(Filters.eq("username", username)).first();
    }

    public User findByEmail(String email) {
        MongoCollection<User> collection = getDatabase().getCollection("users", User.class);
        return collection.find(Filters.eq("email", email)).first();
    }

    public List<User> findAll(UserRole userRole) {
        ArrayList<User> users = new ArrayList<>();
        MongoCollection<User> collection = getDatabase().getCollection("users", User.class);
        collection.find(Filters.eq("userRole", userRole)).into(users);
        return users;
    }

    public List<User> findAllClientsByClientType(ClientType clientType) {
        ArrayList<User> users = new ArrayList<>();
        MongoCollection<User> collection = getDatabase().getCollection("users", User.class);
        collection.find(Filters.eq("clientType", clientType)).into(users);
        return users;
    }

    public void save(User user) {
        MongoCollection<User> collection = getDatabase().getCollection("users", User.class);
        collection.insertOne(user);
    }

    public void update(User user) {
        MongoCollection<User> collection = getDatabase().getCollection("users", User.class);
        BasicDBObject update = new BasicDBObject();
        update.put("username", user.getUsername());
        collection.replaceOne(update, user);
    }

    public void deactivateUser(String username) {
        MongoCollection<User> collection = getDatabase().getCollection("users", User.class);
        collection.updateOne(Filters.eq("username", username), Updates.set("active", false));
    }

    public void activateUser(String username) {
        MongoCollection<User> collection = getDatabase().getCollection("users", User.class);
        collection.updateOne(Filters.eq("username", username), Updates.set("active", true));
    }


//    Client findClientByUsername(String username);
}
