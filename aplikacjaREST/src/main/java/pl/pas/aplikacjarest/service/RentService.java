package pl.pas.aplikacjarest.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pas.aplikacjarest.dto.RentDTO;
import pl.pas.aplikacjarest.dto.client.ClientDTO;
import pl.pas.aplikacjarest.model.Client;
import pl.pas.aplikacjarest.model.Rent;
import pl.pas.aplikacjarest.model.Room;
import pl.pas.aplikacjarest.model.User;
import pl.pas.aplikacjarest.repository.RentRepository;
import pl.pas.aplikacjarest.repository.RoomRepository;
import pl.pas.aplikacjarest.repository.UserRepository;

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

    private boolean rentExist(ObjectId id) {
        MongoCollection<Rent> collection = rentRepository.readAll();
        return collection.find(Filters.eq("_id", id)).first() == null;
    }

    //TODO: ogarnac fuszere
    public RentDTO rentRoom(RentDTO rentDTO) {
       // if (rentExist(rentDTO.getId())) {
//            Client client = new Client(
//                    rentDTO.getClient().getFirstName(),
//                    rentDTO.getClient().getLastName(),
//                    rentDTO.getClient().getUsername(),
//                    rentDTO.getClient().getEmail(),
//                    rentDTO.getClient().getPassword(),
//                    rentDTO.getClient().getClientType()
//            );

            Client client = (Client) userRepository.findByUsername(rentDTO.getClientUsername());
            Room room = roomRepository.findByRoomNumber(rentDTO.getRoomNumber());

//            Room room = new Room(
//                    rentDTO.getRoom().getRoomNumber(),
//                    rentDTO.getRoom().getBasePrice(),
//                    rentDTO.getRoom().getRoomCapacity()
//            );

            Rent rent = new Rent(
                    client,
                    room,
                    rentDTO.getBeginTime()
            );

        System.out.println(rent.getClient().getClass());
            rentRepository.create(rent);

        //}
        return rentDTO;
    }

    
}
