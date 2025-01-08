package pl.pas.aplikacjarest.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pas.aplikacjarest.dto.RoomDTO;
import pl.pas.aplikacjarest.exception.RoomAlreadyExistsException;
import pl.pas.aplikacjarest.exception.RoomIsAlreadyRentedException;
import pl.pas.aplikacjarest.exception.RoomNotFoundException;
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
        if (room != null)
            throw new RoomAlreadyExistsException("Room with number " + roomDTO.getRoomNumber() + " already exists");
        Room newRoom = new Room(
                roomDTO.getRoomNumber(),
                roomDTO.getBasePrice(),
                roomDTO.getRoomCapacity()
        );
        ObjectId roomId = roomRepository.save(newRoom);
        RoomDTO newRoomDTO = new RoomDTO(
                newRoom.getRoomNumber(),
                newRoom.getBasePrice(),
                newRoom.getRoomCapacity(),
                newRoom.getRented()
            );
        newRoomDTO.setId(roomId.toString());
        return newRoomDTO;
    }

    public void deleteRoom(ObjectId roomID) {
        Room room = roomRepository.getRoomByID(roomID);
        if (room == null)
            throw new RoomNotFoundException("Room not found");
        if (rentRepository.isRoomCurrentlyRented(roomID) != null)
            throw new RoomIsAlreadyRentedException("Room is currently rented and cannot be deleted");
        roomRepository.delete(roomID);
    }

    public RoomDTO getRoomByID(ObjectId id) {
        Room room = roomRepository.getRoomByID(id);
        if (room == null)
            throw new RoomNotFoundException("Room not found");
        RoomDTO newRoomDTO = new RoomDTO(
                room.getRoomNumber(),
                room.getBasePrice(),
                room.getRoomCapacity(),
                room.getRented()
        );
        newRoomDTO.setId(room.getId().toString());
        return newRoomDTO;
    }

    public void updateRoom(ObjectId roomID, RoomDTO roomDTO) {
        Room room = roomRepository.getRoomByID(roomID);
        if (room == null)
            throw new RoomNotFoundException("Room not found");
        room.setBasePrice(roomDTO.getBasePrice());
        room.setRoomCapacity(roomDTO.getRoomCapacity());
        roomRepository.update(room);
    }

    public List<RoomDTO> getRoomsByRoomCapacity(int roomCapacity) {
        List<Room> rooms = roomRepository.getRoomsByRoomCapacity(roomCapacity);
        return rooms.stream()
                .map(room -> {
                    RoomDTO roomDTO = new RoomDTO(
                            room.getRoomNumber(),
                            room.getBasePrice(),
                            room.getRoomCapacity(),
                            room.getRented()
                    );
                    roomDTO.setId(room.getId().toString());
                    return roomDTO;
                })
                .collect(Collectors.toList());
    }

    public List<RoomDTO> getRoomsByBasePrice(int basePrice) {
        List<Room> rooms = roomRepository.getRoomsByBasePrice(basePrice);
        return rooms.stream()
                .map(room -> {
                    RoomDTO roomDTO = new RoomDTO(
                            room.getRoomNumber(),
                            room.getBasePrice(),
                            room.getRoomCapacity(),
                            room.getRented()
                    );
                    roomDTO.setId(room.getId().toString());
                    return roomDTO;
                })
                .collect(Collectors.toList());
    }

    public List<RoomDTO> findAll() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream()
                .map(room -> {
                    RoomDTO roomDTO = new RoomDTO(
                            room.getRoomNumber(),
                            room.getBasePrice(),
                            room.getRoomCapacity(),
                            room.getRented()
                    );
                    roomDTO.setId(room.getId().toString());
                    return roomDTO;
                })
                .collect(Collectors.toList());
    }
}
