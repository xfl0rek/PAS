package pl.pas.aplikacjarest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class RentDTO {
    @NotNull(message = "Client username cannot be null")
    @Size(min = 5, max = 30, message = "Username must be between 5 and 30 characters")
    private String clientUsername;
    @NotNull(message = "Room number cannot be null")
    @Min(value = 1, message = "Room number must be greater than 0")
    private int roomNumber;
    @NotNull(message = "Begin time cannot be null")
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
