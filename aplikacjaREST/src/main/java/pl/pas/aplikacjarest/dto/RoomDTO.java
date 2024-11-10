package pl.pas.aplikacjarest.dto;

public class RoomDTO {
    private int basePrice;
    private int roomCapacity;

    public RoomDTO(int basePrice, int roomCapacity) {
        this.basePrice = basePrice;
        this.roomCapacity = roomCapacity;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }
}
