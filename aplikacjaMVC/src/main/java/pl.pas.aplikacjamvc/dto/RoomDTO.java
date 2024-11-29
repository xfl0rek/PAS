package pl.pas.aplikacjamvc.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class RoomDTO {
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

    public RoomDTO(int roomNumber, int basePrice, int roomCapacity) {
        this.roomNumber = roomNumber;
        this.basePrice = basePrice;
        this.roomCapacity = roomCapacity;
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
