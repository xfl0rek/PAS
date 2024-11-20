package pl.pas.aplikacjarest.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
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

    public User findByID(ObjectId userID) {
        MongoCollection<User> collection = getDatabase().getCollection("users", User.class);
        return collection.find(Filters.eq("_id", userID)).first();
    }

    public List<User> findAllByRole(UserRole userRole) {
        ArrayList<User> users = new ArrayList<>();
        MongoCollection<User> collection = getDatabase().getCollection("users", User.class);
        collection.find(Filters.eq("userRole", userRole)).into(users);
        return users;
    }

    public List<User> findAll() {
        ArrayList<User> users = new ArrayList<>();
        MongoCollection<User> collection = getDatabase().getCollection("users", User.class);
        collection.find().into(users);
        return users;
    }

    public ObjectId save(User user) {
        MongoCollection<User> collection = getDatabase().getCollection("users", User.class);
        InsertOneResult result = collection.insertOne(user);
        return result.getInsertedId().asObjectId().getValue();
    }

    public void update(User user) {
        MongoCollection<User> collection = getDatabase().getCollection("users", User.class);
        BasicDBObject update = new BasicDBObject();
        update.put("username", user.getUsername());
        collection.replaceOne(update, user);
    }

    public void deactivateUser(ObjectId userID) {
        MongoCollection<User> collection = getDatabase().getCollection("users", User.class);
        collection.updateOne(Filters.eq("_id", userID), Updates.set("active", false));
    }

    public void activateUser(ObjectId userID) {
        MongoCollection<User> collection = getDatabase().getCollection("users", User.class);
        collection.updateOne(Filters.eq("_id", userID), Updates.set("active", true));
    }

    public List<User> findUsersByPartialUsername(String partialUsername) {
        MongoCollection<User> collection = getDatabase().getCollection("users", User.class);
        Bson filter = Filters.regex("username", partialUsername, "i");
        List<User> users = new ArrayList<>();
        collection.find(filter).into(users);
        return users;
    }

}
