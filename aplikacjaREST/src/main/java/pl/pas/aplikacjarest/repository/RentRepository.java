package pl.pas.aplikacjarest.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import pl.pas.aplikacjarest.exception.RentTransactionException;
import pl.pas.aplikacjarest.model.Rent;
import pl.pas.aplikacjarest.model.Room;
import pl.pas.aplikacjarest.model.User;

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
            MongoCollection<Room> roomCollection = getDatabase().getCollection("rooms", Room.class);
            Bson filter = Filters.eq("roomNumber", rent.getRoom().getRoomNumber());
            Bson update = Updates.inc("rented", 1);
            roomCollection.updateOne(clientSession, filter, update);

            MongoCollection<Rent> rentCollection = getDatabase().getCollection("rents", Rent.class);
            rentCollection.insertOne(clientSession, rent);

            clientSession.commitTransaction();
        } catch (Exception e) {
            if (clientSession.hasActiveTransaction())
                clientSession.abortTransaction();
            throw new RentTransactionException("Transaction failed", e);
        }
    }

    public Rent findByID(ObjectId id) {
        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
        return collection.find(Filters.eq("_id", id)).first();
    }

    public List<Rent> findAll() {
        ArrayList<Rent> rents = new ArrayList<>();
        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
        collection.find().into(rents);
        return rents;
    }


//    public Rent getRentByRoomNumber(int roomNumber) {
//        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
//        Bson filter = Filters.eq("room.roomNumber", roomNumber);
//        return collection.find(filter).first();
//    }

    public Rent isRoomCurrentlyRented(int roomNumber) {
        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
        Bson filter = Filters.and(
                Filters.eq("room.roomNumber", roomNumber),
                Filters.eq("archive", false)
        );
        return collection.find(filter).first();
    }

    //Zakonczonej rezerwacji nie mozna edytowac
    public void update(Rent rent) {
        ClientSession clientSession = getMongoClient().startSession();
        try (clientSession) {
            clientSession.startTransaction();

            if (rent.isArchive()) {
                MongoCollection<Room> roomCollection = getDatabase().getCollection("rooms", Room.class);
                Bson roomFilter = Filters.eq("roomNumber", rent.getRoom().getRoomNumber());
                Bson update = Updates.inc("rented", -1);
                roomCollection.updateOne(clientSession, roomFilter, update);
            }

            MongoCollection<Rent> rentCollection = getDatabase().getCollection("rents", Rent.class);

            Bson rentFilter = Filters.eq("_id", rent.getId());
            Bson updates = Updates.combine(
                    Updates.set("client", rent.getClient()),
                    Updates.set("room", rent.getRoom()),
                    Updates.set("begintime", rent.getBeginTime()),
                    Updates.set("endtime", rent.getEndTime()),
                    Updates.set("rentcost", rent.getRentCost()),
                    Updates.set("archive", rent.isArchive())
            );
            rentCollection.updateOne(clientSession, rentFilter, updates);

            clientSession.commitTransaction();
        } catch (Exception e) {
            if (clientSession.hasActiveTransaction())
                clientSession.abortTransaction();
            throw new RentTransactionException("Transaction failed", e);
        }
    }

    public void delete(ObjectId id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", id);
        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
        collection.deleteOne(query);
    }

//    public void deleteRoomByRoomNumber(int roomNumber) {
//        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
//        Bson filter = Filters.eq("room.roomNumber", roomNumber);
//        collection.deleteOne(filter);
//    }

    public MongoCollection<Rent> readAll() {
        return getDatabase().getCollection("rents", Rent.class);
    }
}
