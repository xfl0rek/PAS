package pl.pas.aplikacjarest.dto;

import org.bson.types.ObjectId;
import pl.pas.aplikacjarest.dto.client.ClientDTO;
import pl.pas.aplikacjarest.dto.RoomDTO;

import java.time.LocalDateTime;

public class RentDTO {
    private String clientUsername;
    private int roomNumber;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;

    public RentDTO(String clientUsername, int roomNumber, LocalDateTime beginTime, LocalDateTime endTime) {
        this.clientUsername = clientUsername;
        this.roomNumber = roomNumber;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
