package pl.pas.aplikacjarest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.pas.aplikacjarest.model.Client;
import pl.pas.aplikacjarest.model.User;
import pl.pas.aplikacjarest.model.UserRole;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
    User findByEmail(String email);
    //List<User> findAll(UserRole userRole);
    Client findClientByUsername(String username);
}
