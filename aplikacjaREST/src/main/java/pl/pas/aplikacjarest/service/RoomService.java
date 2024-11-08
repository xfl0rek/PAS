package pl.pas.aplikacjarest.service;

import org.springframework.stereotype.Service;
import pl.pas.aplikacjarest.model.Room;

@Service
public class RoomService {
    public RoomService() {}

    public Room getRoom(long id) {
        return new Room(id, 1000, 2);
    }
}
