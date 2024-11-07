package pl.pas.aplikacjarest.model;

public class Room {
    private long roomNumber;
    private int basePrice;
    private int roomCapacity;

    public Room(long roomNumber, int basePrice, int roomCapacity) {
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

    //na testa
    @Override
    public String toString() {
        return "roomNumber=" + roomNumber +
                ", basePrice=" + basePrice +
                ", roomCapacity=" + roomCapacity;
    }
}
