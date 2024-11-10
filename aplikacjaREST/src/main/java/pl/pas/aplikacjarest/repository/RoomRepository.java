package pl.pas.aplikacjarest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.pas.aplikacjarest.model.Room;

public interface RoomRepository extends MongoRepository<Room, String> {
    Room findByRoomNumber(long number);
}
