package pl.pas.aplikacjarest.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pas.aplikacjarest.dto.RoomDTO;
import pl.pas.aplikacjarest.model.Room;
import pl.pas.aplikacjarest.repository.RentRepository;
import pl.pas.aplikacjarest.repository.RoomRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    RoomRepository roomRepository;
    RentRepository rentRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, RentRepository rentRepository) {
        this.roomRepository = roomRepository;
        this.rentRepository = rentRepository;
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
        if (room != null && rentRepository.isRoomCurrentlyRented(roomNumber) == null) {
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

    public List<RoomDTO> getRoomsByRoomCapacity(int roomCapacity) {
        List<Room> rooms = roomRepository.getRoomsByRoomCapacity(roomCapacity);
        return rooms.stream()
                .map(room -> new RoomDTO(
                        room.getRoomNumber(),
                        room.getBasePrice(),
                        room.getRoomCapacity()
                ))
                .collect(Collectors.toList());
    }

    public List<RoomDTO> getRoomsByBasePrice(int basePrice) {
        List<Room> rooms = roomRepository.getRoomsByBasePrice(basePrice);
        return rooms.stream()
                .map(room -> new RoomDTO(
                        room.getRoomNumber(),
                        room.getBasePrice(),
                        room.getRoomCapacity()
                ))
                .collect(Collectors.toList());
    }
}
