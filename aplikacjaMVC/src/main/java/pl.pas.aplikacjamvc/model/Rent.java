package pl.pas.aplikacjamvc.model;

import org.bson.types.ObjectId;

import java.time.Duration;
import java.time.LocalDateTime;

public class Rent {
    private ObjectId id;
    private Client client;
    private Room room;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private double rentCost;
    private boolean isArchive;

    public Rent(Client client, Room room, LocalDateTime beginTime) {
        this.client = client;
        this.room = room;
        this.beginTime = beginTime;
    }

    public Rent() {

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
        return Math.round(100 * getRentDays() * room.getBasePrice()) / 100.0;
    }
}
