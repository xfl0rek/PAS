package pl.pas.aplikacjarest.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pas.aplikacjarest.dto.RentDTO;
import pl.pas.aplikacjarest.exception.*;
import pl.pas.aplikacjarest.model.Client;
import pl.pas.aplikacjarest.model.Rent;
import pl.pas.aplikacjarest.model.Room;
import pl.pas.aplikacjarest.repository.RentRepository;
import pl.pas.aplikacjarest.repository.RoomRepository;
import pl.pas.aplikacjarest.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RentService {
    UserRepository userRepository;
    RoomRepository roomRepository;
    RentRepository rentRepository;

    @Autowired
    public RentService(UserRepository userRepository, RoomRepository roomRepository, RentRepository rentRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.rentRepository = rentRepository;
    }


    public RentDTO rentRoom(RentDTO rentDTO) {
        Client client = (Client) userRepository.findByUsername(rentDTO.getClientUsername());
        if (client == null)
            throw new UserNotFoundException("Client not found");
        if (!client.isActive())
            throw new InactiveUserException("Client is inactive and cannot rent room");
        Room room = roomRepository.findByRoomNumber(rentDTO.getRoomNumber());
        if (room == null)
            throw new RoomNotFoundException("Room not found");
        Rent rent = new Rent(
                client,
                room,
                rentDTO.getBeginTime()
        );
        ObjectId rentId = rentRepository.create(rent);
        rentDTO.setId(rentId.toString());
        return rentDTO;
    }

    public RentDTO returnRoom(ObjectId rentID, RentDTO rentDTO) {
        Rent rent = rentRepository.findByID(rentID);
        if (rent == null)
            throw new RentNotFoundException("Rent not found");
        if (rent.isArchive())
            throw new RentAlreadyEndedException("Rent already ended");
        if (rentDTO.getEndTime() == null)
            throw new RentTransactionException("End time cannot be null");

        rent.endRent(rentDTO.getEndTime());
        RentDTO returnRentDTO = new RentDTO(
                rent.getClient().getUsername(),
                rent.getRoom().getRoomNumber(),
                rent.getBeginTime(),
                rent.getEndTime()
        );
        returnRentDTO.setId(rentID.toString());
        rentRepository.update(rent);
        return returnRentDTO;
    }

    public void updateRent(ObjectId rentID, RentDTO rentDTO) {
        Rent rent = rentRepository.findByID(rentID);
        if (rent == null)
            throw new RentNotFoundException("Rent not found");
        if (rent.isArchive())
            throw new RentAlreadyEndedException("Rent already ended");


        Room room = roomRepository.findByRoomNumber(rentDTO.getRoomNumber());
        if (room == null)
            throw new RoomNotFoundException("Room not found");
        Client client = (Client) userRepository.findByUsername(rentDTO.getClientUsername());
        if (client == null)
            throw new UserNotFoundException("Client not found");
        rent.setClient(client);
        rent.setRoom(room);
        rent.setBeginTime(rentDTO.getBeginTime());
        rentRepository.update(rent);
    }

    public void deleteRent(ObjectId rentID) {
        Rent rent = rentRepository.findByID(rentID);
        if (rent == null)
            throw new RentNotFoundException("Rent not found");
        if (rent.isArchive())
            throw new RentNotEndedException("Rent ended and cannot be deleted");
        rentRepository.delete(rentID);
    }

    public RentDTO getRentByID(ObjectId rentID) {
        Rent rent = rentRepository.findByID(rentID);
        if (rent == null)
            throw new RentNotFoundException("Rent not found");
        RentDTO rentDTO = new RentDTO(
                rent.getClient().getUsername(),
                rent.getRoom().getRoomNumber(),
                rent.getBeginTime(),
                rent.getEndTime()
        );
        rentDTO.setId(rentID.toString());
        return rentDTO;
    }

    public List<RentDTO> getAllActiveRentsForUser(ObjectId userID) {
        return rentRepository.findAllActiveRentsForUser(userID).stream()
                .map(rent -> {
                    RentDTO rentDTO = new RentDTO(
                            rent.getClient().getUsername(),
                            rent.getRoom().getRoomNumber(),
                            rent.getBeginTime(),
                            rent.getEndTime()
                    );
                    rentDTO.setId(rent.getId().toString());
                    return rentDTO;
                })
                .toList();
    }

    public List<RentDTO> getAllArchiveRentsForUser(ObjectId userID) {
        return rentRepository.findAllArchiveRentsForUser(userID).stream()
                .map(rent -> {
                    RentDTO rentDTO = new RentDTO(
                            rent.getClient().getUsername(),
                            rent.getRoom().getRoomNumber(),
                            rent.getBeginTime(),
                            rent.getEndTime()
                    );
                    rentDTO.setId(rent.getId().toString());
                    return rentDTO;
                })
                .toList();
    }

    public List<RentDTO> getAllActiveRentsForRoom(ObjectId rentId) {
        return rentRepository.findAllActiveRentsForRoom(rentId).stream()
                .map(rent -> {
                    RentDTO rentDTO = new RentDTO(
                            rent.getClient().getUsername(),
                            rent.getRoom().getRoomNumber(),
                            rent.getBeginTime(),
                            rent.getEndTime()
                    );
                    rentDTO.setId(rent.getId().toString());
                    return rentDTO;
                })
                .toList();
    }

    public List<RentDTO> getAllArchiveRentsForRoom(ObjectId rentId) {
        return rentRepository.findAllArchiveRentsForRoom(rentId).stream()
                .map(rent -> {
                    RentDTO rentDTO = new RentDTO(
                            rent.getClient().getUsername(),
                            rent.getRoom().getRoomNumber(),
                            rent.getBeginTime(),
                            rent.getEndTime()
                    );
                    rentDTO.setId(rent.getId().toString());
                    return rentDTO;
                })
                .toList();
    }

    public List<RentDTO> findAll() {
        return rentRepository.readAll().stream()
                .map(rent -> {
                    RentDTO rentDTO = new RentDTO(
                            rent.getClient().getUsername(),
                            rent.getRoom().getRoomNumber(),
                            rent.getBeginTime(),
                            rent.getEndTime()
                    );
                    rentDTO.setId(rent.getId().toString());
                    return rentDTO;
                })
                .toList();
    }

    public List<RentDTO> getAllRentsForUser(ObjectId userID) {
        return rentRepository.findAllRentsForUser(userID).stream()
                .map(rent -> {
                    RentDTO rentDTO = new RentDTO(
                            rent.getClient().getUsername(),
                            rent.getRoom().getRoomNumber(),
                            rent.getBeginTime(),
                            rent.getEndTime()
                    );
                    rentDTO.setId(rent.getId().toString());
                    return rentDTO;
                })
                .toList();
    }
}
