package pl.pas.aplikacjarest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pas.aplikacjarest.dto.RoomDTO;
import pl.pas.aplikacjarest.model.Room;
import pl.pas.aplikacjarest.repository.RoomRepository;

@Service
public class RoomService {
    RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

//    public RoomDTO createRoom(RoomDTO roomDTO) {
//        Room room = new Room(
//
//        )
//    }

    public Room getRoom(long id) {
        return new Room(id, 1000, 2);
    }
}
