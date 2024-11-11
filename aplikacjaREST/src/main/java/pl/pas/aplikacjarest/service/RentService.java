package pl.pas.aplikacjarest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pas.aplikacjarest.dto.RentDTO;
import pl.pas.aplikacjarest.repository.RentRepository;

@Service
public class RentService {
    private final RentRepository rentRepository;

    @Autowired
    public RentService(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    public RentDTO rentRoom() {
        return null;
    }

}
