package pl.pas.aplikacjarest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;

public class RoomDTO {
    private String id;
    @NotNull(message = "Room number cannot be null")
    @Min(value = 1, message = "Room number must be greater than 0")
    private int roomNumber;
    @NotNull(message = "Base price cannot be null")
    @Min(value = 100, message = "Base price must be greater than 99")
    private int basePrice;
    @NotNull(message = "Room capacity cannot be null")
    @Min(value = 1, message = "Room capacity must be greater than 0")
    @Max(value = 5, message = "Room capacity must be less than 5")
    private int roomCapacity;

    private int isRented;

    public RoomDTO(int roomNumber, int basePrice, int roomCapacity, int isRented) {
        this.roomNumber = roomNumber;
        this.basePrice = basePrice;
        this.roomCapacity = roomCapacity;
        this.isRented = isRented;;
    }

    public String getId() {
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

    public void setId(String id) {
        this.id = id;
    }

    public int getIsRented() {
        return isRented;
    }

    public void setIsRented(int isRented) {
        this.isRented = isRented;
    }
}
