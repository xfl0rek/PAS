package pl.pas.aplikacjarest.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.springframework.stereotype.Repository;
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

    public void save(User user) {
        MongoCollection<User> collection = getDatabase().getCollection("users", User.class);
        collection.insertOne(user);
    }

    public void update(User user) {
        MongoCollection<User> collection = getDatabase().getCollection("users", User.class);
        BasicDBObject update = new BasicDBObject();
        update.put("_id", user.getId());
        collection.replaceOne(update, user);
    }

    public void delete(long id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", id);
        MongoCollection<User> collection = getDatabase().getCollection("users", User.class);
        collection.deleteOne(query);
    }

//    Client findClientByUsername(String username);
}
