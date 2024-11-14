package pl.pas.aplikacjarest.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.springframework.stereotype.Repository;
import pl.pas.aplikacjarest.model.Room;

@Repository
public class RoomRepository extends AbstractMongoRepository {
    @Override
    public void close() throws Exception {

    }

    public RoomRepository() {
        this.initDBConnection();
    }

    public void create(Room room) {
        MongoCollection<Room> collection = getDatabase().getCollection("rooms", Room.class);
        collection.insertOne(room);
    }

    public MongoCollection<Room> readAll() {
        return getDatabase().getCollection("rooms", Room.class);
    }

    public Room read(long id) {
        MongoCollection<Room> collection = getDatabase().getCollection("rooms", Room.class);
        return collection.find(Filters.eq("_id", id)).first();
    }

    public void update(Room room) {
        MongoCollection<Room> collection = getDatabase().getCollection("rooms", Room.class);
        BasicDBObject update = new BasicDBObject();
        update.put("_id", room.getRoomNumber());
        collection.replaceOne(update, room);
    }

    public void delete(long id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", id);
        MongoCollection<Room> collection = getDatabase().getCollection("rooms", Room.class);
        collection.deleteOne(query);
    }

    public Room findByRoomNumber(int roomNumber) {
        MongoCollection<Room> collection = getDatabase().getCollection("rooms", Room.class);
        return collection.find(Filters.eq("roomNumber", roomNumber)).first();
    }

    public void save(Room room) {
        MongoCollection<Room> collection = getDatabase().getCollection("rooms", Room.class);
        collection.insertOne(room);
    }
}
