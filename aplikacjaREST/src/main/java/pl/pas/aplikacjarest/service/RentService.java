package pl.pas.aplikacjarest.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pas.aplikacjarest.dto.RentDTO;
import pl.pas.aplikacjarest.model.Client;
import pl.pas.aplikacjarest.model.Rent;
import pl.pas.aplikacjarest.repository.RentRepository;

import java.util.ArrayList;

@Service
public class RentService {
    RentRepository rentRepository;

    @Autowired
    public RentService(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    private boolean rentExist(ObjectId id) {
        MongoCollection<Rent> collection = rentRepository.readAll();
        return collection.find(Filters.eq("_id", id)).first() == null;
    }

//    public RentDTO rentRoom(RentDTO rentDTO) {
//        if (rentExist(rentDTO.getId())) {
//            Client client = new Client(
//                    rentDTO.getClient().getFirstName(),
//                    rentDTO.getClient().getLastName(),
//                    rentDTO.getClient().getUsername(),
//                    rentDTO.getClient().getEmail(),
//                    rentDTO.getClient().getClientType()
//            );
//            Rent rent = new Rent(
//                    rentDTO.getClient(),
//                    rentDTO.getRoom(),
//                    rentDTO.getBeginTime()
//            );
//            rentRepository.create();
//        }
//    }

}
