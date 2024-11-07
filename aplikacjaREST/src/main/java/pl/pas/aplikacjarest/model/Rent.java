package pl.pas.aplikacjarest.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Rent {
    private long id;
    private Client client;
    private Room room;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private double rentCost;
    private boolean isArchive;

    public Rent(long id, Client client, Room room, LocalDateTime beginTime) {
        this.id = id;
        this.client = client;
        this.room = room;
        this.beginTime = beginTime;
    }

    public long getId() {
        return id;
    }

    public User getClient() {
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

    public void setId(long id) {
        this.id = id;
    }

    public void setUser(Client client) {
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
