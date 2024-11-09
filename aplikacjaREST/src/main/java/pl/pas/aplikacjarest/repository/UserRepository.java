package pl.pas.aplikacjarest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.pas.aplikacjarest.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
    User findByEmail(String email);
}
