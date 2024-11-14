package pl.pas.aplikacjarest.model;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;


public class Room {
    @BsonId
    private ObjectId id;
    @BsonProperty
    private int roomNumber;
    @BsonProperty("baseprice")
    private int basePrice;
    @BsonProperty("roomcapacity")
    private int roomCapacity;

    public Room(@BsonId int roomNumber,
                @BsonProperty("baseprice") int basePrice,
                @BsonProperty("roomcapacity") int roomCapacity) {
        this.id = new ObjectId();
        this.roomNumber = roomNumber;
        this.basePrice = basePrice;
        this.roomCapacity = roomCapacity;
    }

    public Room() {

    }

    public ObjectId getId() {
        return id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }
}
