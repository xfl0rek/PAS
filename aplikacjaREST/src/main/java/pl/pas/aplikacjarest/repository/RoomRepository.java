package pl.pas.aplikacjarest.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import pl.pas.aplikacjarest.dto.RoomDTO;
import pl.pas.aplikacjarest.model.Room;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomRepository extends AbstractMongoRepository {
    @Override
    public void close() throws Exception {

    }

    public RoomRepository() {
        this.initDBConnection();
    }


    public MongoCollection<Room> readAll() {
        return getDatabase().getCollection("rooms", Room.class);
    }


    public void update(Room room) {
        MongoCollection<Room> collection = getDatabase().getCollection("rooms", Room.class);
        BasicDBObject update = new BasicDBObject();
        update.put("roomNumber", room.getRoomNumber());
        collection.replaceOne(update, room);
    }

    public Room findByRoomNumber(int roomNumber) {
        MongoCollection<Room> collection = getDatabase().getCollection("rooms", Room.class);
        return collection.find(Filters.eq("roomNumber", roomNumber)).first();
    }

    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        MongoCollection<Room> collection = getDatabase().getCollection("rooms", Room.class);
        collection.find().into(rooms);
        return rooms;
    }

    public ObjectId save(Room room) {
        MongoCollection<Room> collection = getDatabase().getCollection("rooms", Room.class);
        InsertOneResult result = collection.insertOne(room);
        return result.getInsertedId().asObjectId().getValue();
    }

    public void delete(ObjectId roomID) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", roomID);
        MongoCollection<Room> collection = getDatabase().getCollection("rooms", Room.class);
        collection.deleteOne(query);
    }

    public Room getRoomByID(ObjectId id) {
        MongoCollection<Room> collection = getDatabase().getCollection("rooms", Room.class);
        return collection.find(Filters.eq("_id", id)).first();
    }

    public List<Room> getRoomsByRoomCapacity(int roomCapacity) {
        MongoCollection<Room> collection = getDatabase().getCollection("rooms", Room.class);
        return collection.find(Filters.eq("roomcapacity", roomCapacity)).into(new ArrayList<>());
    }

    public List<Room> getRoomsByBasePrice(int basePrice) {
        MongoCollection<Room> collection = getDatabase().getCollection("rooms", Room.class);
        return collection.find(Filters.eq("baseprice", basePrice)).into(new ArrayList<>());
    }
}
