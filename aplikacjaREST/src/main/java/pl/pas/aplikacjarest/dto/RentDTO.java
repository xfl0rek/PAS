package pl.pas.aplikacjarest.dto;

import org.bson.types.ObjectId;
import pl.pas.aplikacjarest.dto.client.ClientDTO;
import pl.pas.aplikacjarest.dto.RoomDTO;

import java.time.LocalDateTime;

public class RentDTO {
    private ObjectId id;
    private ClientDTO client;
    private RoomDTO room;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;

    public RentDTO(ClientDTO client, RoomDTO room, LocalDateTime beginTime, LocalDateTime endTime) {
        this.client = client;
        this.room = room;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public ObjectId getId() {
        return id;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
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
