package pl.pas.aplikacjarest.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pas.aplikacjarest.dto.RentDTO;
import pl.pas.aplikacjarest.service.RentService;

import java.time.LocalDateTime;

@RestController
public class RentController {
    private RentService rentService;

    @Autowired
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @PostMapping("/client/rentRoom")
    public ResponseEntity<RentDTO> rentRoom(@RequestBody RentDTO rentDTO) {
        RentDTO rent = rentService.rentRoom(rentDTO);
        return ResponseEntity.ok(rent);
    }

    @PostMapping("/client/returnRoom/{id}")
    public ResponseEntity<RentDTO> returnRoom(@PathVariable String id,
                                              @RequestParam LocalDateTime endTime) {
        ObjectId rentID = new ObjectId(id);
        RentDTO rent = rentService.returnRoom(rentID, endTime);
        return ResponseEntity.ok(rent);
    }

    @PostMapping("/manager/updateRent/{id}")
    public void updateRent(@PathVariable String id, @RequestBody RentDTO rentDTO) throws Exception { //TODO usunac throws Exception
        ObjectId rentID = new ObjectId(id);
        rentService.updateRent(rentID, rentDTO);
    }

    @PostMapping("/manager/deleteRent/{id}")
    public void deleteRent(@PathVariable String id) {
        ObjectId rentID = new ObjectId(id);
        rentService.deleteRent(rentID);
    }

    @GetMapping("/manager/getRentByID/{id}")
    public ResponseEntity<RentDTO> getRentByRoomNumber(@PathVariable String id) {
        ObjectId rentID = new ObjectId(id);
        RentDTO rentDTO = rentService.getRentByID(rentID);
        return ResponseEntity.ok(rentDTO);
    }
}
