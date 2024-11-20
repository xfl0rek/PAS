package pl.pas.aplikacjarest.controller;

import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pas.aplikacjarest.dto.RentDTO;
import pl.pas.aplikacjarest.service.RentService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class RentController {
    private RentService rentService;

    @Autowired
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @PostMapping("/client/rentRoom")
    public ResponseEntity<RentDTO> rentRoom(@Valid @RequestBody RentDTO rentDTO) {
        RentDTO rent = rentService.rentRoom(rentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(rent);
    }

    @PostMapping("/client/returnRoom/{id}")
    public ResponseEntity<RentDTO> returnRoom(@PathVariable String id,
                                              @RequestParam LocalDateTime endTime) {
        ObjectId rentID = new ObjectId(id);
        RentDTO rent = rentService.returnRoom(rentID, endTime);
        return ResponseEntity.ok(rent);
    }

    @PostMapping("/manager/updateRent/{id}")
    public ResponseEntity<Void> updateRent(@PathVariable String id, @Valid @RequestBody RentDTO rentDTO) throws Exception { //TODO usunac throws Exception
        ObjectId rentID = new ObjectId(id);
        rentService.updateRent(rentID, rentDTO);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/manager/deleteRent/{id}")
    public ResponseEntity<Void> deleteRent(@PathVariable String id) {
        ObjectId rentID = new ObjectId(id);
        rentService.deleteRent(rentID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/manager/getRentByID/{id}")
    public ResponseEntity<RentDTO> getRentByID(@PathVariable String id) {
        ObjectId rentID = new ObjectId(id);
        RentDTO rentDTO = rentService.getRentByID(rentID);
        return ResponseEntity.ok(rentDTO);
    }

    @GetMapping("/manager/getAllActiveRentsForUser/{id}")
    public ResponseEntity<List<RentDTO>> getAllActiveRentsForUser(@PathVariable String id) {
        ObjectId userID = new ObjectId(id);
        List<RentDTO> rentDTO = rentService.getAllActiveRentsForUser(userID);
        return ResponseEntity.ok(rentDTO);
    }

    @GetMapping("/manager/getAllArchiveRentsForUser/{id}")
    public ResponseEntity<List<RentDTO>> getAllArchiveRentsForUser(@PathVariable String id) {
        ObjectId userID = new ObjectId(id);
        List<RentDTO> rentDTO = rentService.getAllArchiveRentsForUser(userID);
        return ResponseEntity.ok(rentDTO);
    }

    @GetMapping("/manager/getAllActiveRentsForRoom/{id}")
    public ResponseEntity<List<RentDTO>> getAllActiveRentsForRoom(@PathVariable String id) {
        ObjectId rentID = new ObjectId(id);
        List<RentDTO> rentDTO = rentService.getAllActiveRentsForRoom(rentID);
        return ResponseEntity.ok(rentDTO);
    }

    @GetMapping("/manager/getAllArchiveRentsForRoom/{id}")
    public ResponseEntity<List<RentDTO>> getAllArchiveRentsForRoom(@PathVariable String id) {
        ObjectId userID = new ObjectId(id);
        List<RentDTO> rentDTO = rentService.getAllArchiveRentsForRoom(userID);
        return ResponseEntity.ok(rentDTO);
    }
}
