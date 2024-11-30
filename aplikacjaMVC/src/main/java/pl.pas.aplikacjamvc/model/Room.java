package pl.pas.aplikacjamvc.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.bson.types.ObjectId;


public class Room {
    private ObjectId id;
    @Min(1)
    @Max(100)
    private int roomNumber;
    @Min(500)
    @Max(10000)
    private int basePrice;
    @Min(1)
    @Max(5)
    private int roomCapacity;

    @Min(0)
    @Max(1)
    private int rented = 0;

    public Room(int roomNumber, int basePrice, int roomCapacity) {
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
