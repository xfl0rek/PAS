package pl.pas.aplikacjarest.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pas.aplikacjarest.dto.RoomDTO;
import pl.pas.aplikacjarest.model.Room;
import pl.pas.aplikacjarest.repository.RoomRepository;

import java.util.List;

@Service
public class RoomService {
    RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public RoomDTO createRoom(RoomDTO roomDTO) {
        Room room = roomRepository.findByRoomNumber(roomDTO.getRoomNumber());
        if (room == null) {
            Room newRoom = new Room(
                    roomDTO.getRoomNumber(),
                    roomDTO.getBasePrice(),
                    roomDTO.getRoomCapacity()
            );
            roomRepository.save(newRoom);
            return new RoomDTO(
                    newRoom.getRoomNumber(),
                    newRoom.getBasePrice(),
                    newRoom.getRoomCapacity()
            );
        }
        return null;
    }

    public void deleteRoom(int roomNumber) {
        Room room = roomRepository.findByRoomNumber(roomNumber);
        if (room != null) {
            roomRepository.delete(roomNumber);
        }
    }

    public RoomDTO getRoomByID(ObjectId id) {
        Room room = roomRepository.getRoomByID(id);
        return new RoomDTO(
                room.getRoomNumber(),
                room.getBasePrice(),
                room.getRoomCapacity()
        );
    }

    public void updateRoom(RoomDTO roomDTO) {
        Room room = roomRepository.findByRoomNumber(roomDTO.getRoomNumber());
        room.setBasePrice(roomDTO.getBasePrice());
        room.setRoomCapacity(roomDTO.getRoomCapacity());
        roomRepository.save(room);
    }
}
