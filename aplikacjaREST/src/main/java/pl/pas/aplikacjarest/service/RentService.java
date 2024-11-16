package pl.pas.aplikacjarest.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pas.aplikacjarest.dto.RentDTO;
import pl.pas.aplikacjarest.dto.RoomDTO;
import pl.pas.aplikacjarest.dto.client.ClientDTO;
import pl.pas.aplikacjarest.model.Client;
import pl.pas.aplikacjarest.model.Rent;
import pl.pas.aplikacjarest.model.Room;
import pl.pas.aplikacjarest.model.User;
import pl.pas.aplikacjarest.repository.RentRepository;
import pl.pas.aplikacjarest.repository.RoomRepository;
import pl.pas.aplikacjarest.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
        Room room = roomRepository.findByRoomNumber(rentDTO.getRoomNumber());
        Rent rent = new Rent(
                client,
                room,
                rentDTO.getBeginTime()
        );

        rentRepository.create(rent);
        return rentDTO;
    }

    public RentDTO returnRoom(ObjectId rentID, LocalDateTime endTime) {
        Rent rent = rentRepository.findByID(rentID);
        if (rent == null)
            return null; //TODO dodac wyjatek
        if (rent.isArchive())
            return null; //TODO inny wyjatek

        rent.endRent(endTime);
        RentDTO rentDTO = new RentDTO(
                rent.getClient().getUsername(),
                rent.getRoom().getRoomNumber(),
                rent.getBeginTime(),
                rent.getEndTime()
        );
        rentRepository.update(rent);
        return rentDTO;
    }

    public void updateRent(ObjectId rentID, String clientUsername, int roomNumber, LocalDateTime beginTime) throws Exception { //usunac throws Exception
        Rent rent = rentRepository.findByID(rentID);
        if (rent == null)
            throw new Exception("Nie ma takiego wypozyczenia"); //TODO dodac wyjatek
        if (rent.isArchive())
            throw new Exception("Nie mozna modyfikowac zakonczonego wypozyczenia"); //TODO inny wyjatek

        Room room = roomRepository.findByRoomNumber(roomNumber);
        Client client = (Client) userRepository.findByUsername(clientUsername);
        rent.setClient(client);
        rent.setRoom(room);
        rent.setBeginTime(beginTime);
        rentRepository.update(rent);
    }

    public void deleteRent(ObjectId rentID) {
        Rent rent = rentRepository.findByID(rentID);
        if (rent.isArchive()) {
            rentRepository.delete(rentID);
        }
    }

    public RentDTO getRentByID(ObjectId rentID) {
        Rent rent = rentRepository.findByID(rentID);
        return new RentDTO(
                rent.getClient().getUsername(),
                rent.getRoom().getRoomNumber(),
                rent.getBeginTime(),
                rent.getEndTime()
        );
    }
}
