package pl.pas.aplikacjarest.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import pl.pas.aplikacjarest.model.Rent;
import pl.pas.aplikacjarest.model.Room;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RentRepository extends AbstractMongoRepository {
    @Override
    public void close() throws Exception {

    }

    public RentRepository() {
        this.initDBConnection();
    }

    public void create(Rent rent) {
        ClientSession clientSession = getMongoClient().startSession();
        try (clientSession) {
            clientSession.startTransaction();
//            MongoCollection<Room> roomCollection = getDatabase().getCollection("rooms", Room.class);
//            Bson filter = Filters.eq("_id", rent.getRoom().getRoomNumber());
//            Bson update = Updates.inc("rented", 1);
//            roomCollection.updateOne(clientSession, filter, update);

            MongoCollection<Rent> rentCollection = getDatabase().getCollection("rents", Rent.class);
            rentCollection.insertOne(clientSession, rent);

            clientSession.commitTransaction();
        } catch (Exception e) {
            System.out.println("nie ok");
            if (clientSession.hasActiveTransaction())
                clientSession.abortTransaction();
            throw e;
        }
    }

    public Rent findByID(long id) {
        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
        return collection.find().first();
    }

    public List<Rent> findAll() {
        ArrayList<Rent> rents = new ArrayList<>();
        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
        collection.find().into(rents);
        return rents;
    }

    public void update(Rent rent) {
        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
        BasicDBObject update = new BasicDBObject();
        update.put("_id", rent.getId());
        collection.replaceOne(update, rent);
    }

    public void delete(long id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", id);
        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
        collection.deleteOne(query);
    }

    public MongoCollection<Rent> readAll() {
        return getDatabase().getCollection("rents", Rent.class);
    }
}
