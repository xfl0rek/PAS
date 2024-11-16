package pl.pas.aplikacjarest.model;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.Duration;
import java.time.LocalDateTime;

public class Rent {
    @BsonId
    private ObjectId id;
    @BsonProperty("client")
    private Client client;
    @BsonProperty("room")
    private Room room;
    @BsonProperty("begintime")
    private LocalDateTime beginTime;
    @BsonProperty("endtime")
    private LocalDateTime endTime;
    @BsonProperty("rentcost")
    private double rentCost;
    @BsonProperty("isarchive")
    private boolean isArchive;

    public Rent(@BsonProperty("client") Client client,
                @BsonProperty("room") Room room,
                @BsonProperty("begintime") LocalDateTime beginTime) {
        this.client = client;
        this.room = room;
        this.beginTime = beginTime;
    }

    public ObjectId getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getRentCost() {
        return rentCost;
    }

    public boolean isArchive() {
        return isArchive;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setRentCost(double rentCost) {
        this.rentCost = rentCost;
    }

    public void setArchive(boolean archive) {
        isArchive = archive;
    }

    public void endRent(LocalDateTime endTime) {
        if (this.endTime == null) {
            if (endTime == null) {
                setEndTime(LocalDateTime.now());
            } else {
                if (endTime.isAfter(beginTime)) {
                    setEndTime(endTime);
                } else {
                    setEndTime(beginTime);
                }
            }
            setArchive(true);
            rentCost = calculateRentCost();
        }
    }

    public long getRentDays() {
        if (endTime == null) {
            return 0;
        }

        Duration period = Duration.between(beginTime, endTime);
        long days = period.toHours() / 24;

        if (period.toHours() % 24 >= 1) {
            days += 1;
        }

        return days;
    }

    private double calculateRentCost() {
        return Math.round(100 * client.applyDiscount(getRentDays() * room.getBasePrice())) / 100.0;
    }
}
