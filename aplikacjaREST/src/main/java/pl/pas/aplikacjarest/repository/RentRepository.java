package pl.pas.aplikacjarest.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import pl.pas.aplikacjarest.exception.RentTransactionException;
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

    public ObjectId create(Rent rent) {
        ClientSession clientSession = getMongoClient().startSession();
        try (clientSession) {
            clientSession.startTransaction();
            MongoCollection<Room> roomCollection = getDatabase().getCollection("rooms", Room.class);
            Bson filter = Filters.eq("roomNumber", rent.getRoom().getRoomNumber());
            Bson update = Updates.inc("rented", 1);
            roomCollection.updateOne(clientSession, filter, update);

            MongoCollection<Rent> rentCollection = getDatabase().getCollection("rents", Rent.class);
            InsertOneResult result = rentCollection.insertOne(clientSession, rent);

            clientSession.commitTransaction();
            return result.getInsertedId().asObjectId().getValue();
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

    public List<Rent> findAllActiveRentsForUser(ObjectId userId) {
        ArrayList<Rent> rents = new ArrayList<>();
        Bson filter = Filters.and(Filters.eq("client._id", userId), Filters.eq("archive", false));
        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
        collection.find(filter).into(rents);
        return rents;
    }

    public List<Rent> findAllArchiveRentsForUser(ObjectId userId) {
        ArrayList<Rent> rents = new ArrayList<>();
        Bson filter = Filters.and(Filters.eq("client._id", userId), Filters.eq("archive", true));
        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
        collection.find(filter).into(rents);
        return rents;
    }

    public List<Rent> findAllActiveRentsForRoom(ObjectId roomId) {
        ArrayList<Rent> rents = new ArrayList<>();
        Bson filter = Filters.and(Filters.eq("room._id", roomId), Filters.eq("archive", false));
        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
        collection.find(filter).into(rents);
        return rents;
    }

    public List<Rent> findAllArchiveRentsForRoom(ObjectId roomId) {
        ArrayList<Rent> rents = new ArrayList<>();
        Bson filter = Filters.and(Filters.eq("room._id", roomId), Filters.eq("archive", true));
        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
        collection.find(filter).into(rents);
        return rents;
    }

    public Rent isRoomCurrentlyRented(ObjectId roomID) {
        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
        Bson filter = Filters.and(
                Filters.eq("room._id", roomID),
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
        ClientSession clientSession = getMongoClient().startSession();
        try (clientSession) {
            clientSession.startTransaction();

            MongoCollection<Rent> rentCollection = getDatabase().getCollection("rents", Rent.class);
            Rent rent = rentCollection.find(Filters.eq("_id", id)).first();

            MongoCollection<Room> roomCollection = getDatabase().getCollection("rooms", Room.class);
            Bson roomFilter = Filters.eq("roomNumber", rent.getRoom().getRoomNumber());
            Bson update = Updates.inc("rented", -1);
            roomCollection.updateOne(clientSession, roomFilter, update);

            rentCollection.deleteOne(clientSession, Filters.eq("_id", id));


            clientSession.commitTransaction();
        } catch (Exception e) {
            if (clientSession.hasActiveTransaction())
                clientSession.abortTransaction();
            throw new RentTransactionException("Transaction failed", e);
        }

        BasicDBObject query = new BasicDBObject();
        query.put("_id", id);
        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
        collection.deleteOne(query);
    }

    public List<Rent> findAllRentsForUser(ObjectId userId) {
        ArrayList<Rent> rents = new ArrayList<>();
        Bson filter = Filters.eq("client._id", userId);
        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
        collection.find(filter).into(rents);
        return rents;
    }

    public List<Rent> readAll() {
        ArrayList<Rent> rents = new ArrayList<>();
        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
        collection.find().into(rents);
        return rents;
    }
}
