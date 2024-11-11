package pl.pas.aplikacjarest.model;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;



public class Room {
    @BsonId
    private long roomNumber;
    @BsonProperty("baseprice")
    private int basePrice;
    @BsonProperty("roomcapacity")
    private int roomCapacity;

    public Room(@BsonId long roomNumber,
                @BsonProperty("baseprice") int basePrice,
                @BsonProperty("roomcapacity") int roomCapacity) {
        this.roomNumber = roomNumber;
        this.basePrice = basePrice;
        this.roomCapacity = roomCapacity;
    }

    public long getRoomNumber() {
        return roomNumber;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomNumber(long roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }
}
