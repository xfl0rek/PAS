package pl.pas.aplikacjarest.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;


public class Room {
    @BsonId
    private ObjectId id;
    @BsonProperty
    @Min(1)
    @Max(100)
    private int roomNumber;
    @BsonProperty("baseprice")
    @Min(500)
    @Max(10000)
    private int basePrice;
    @BsonProperty("roomcapacity")
    @Min(1)
    @Max(5)
    private int roomCapacity;

    @BsonProperty("rented")
    @Min(0)
    @Max(1)
    private int rented = 0;

    public Room(@BsonProperty int roomNumber,
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

    public void setId(ObjectId id) {
        this.id = id;
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

    public int getRented() {
        return rented;
    }
}
