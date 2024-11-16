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

    @PostMapping("/client/returnRoom")
    public ResponseEntity<RentDTO> returnRoom(@RequestParam ObjectId rentID,
                                              @RequestParam LocalDateTime endTime) {
        RentDTO rent = rentService.returnRoom(rentID, endTime);
        return ResponseEntity.ok(rent);
    }

    @PostMapping("/manager/updateRent")
    public void updateRent(@RequestParam ObjectId rentID,
                           @RequestParam String clientUsername,
                           @RequestParam int roomNumber,
                           @RequestParam LocalDateTime beginTime) throws Exception { //TODO usunac throws Exception
        rentService.updateRent(rentID, clientUsername, roomNumber, beginTime);
    }

    @PostMapping("/manager/deleteRent")
    public void deleteRent(@RequestParam ObjectId rentID) {
        rentService.deleteRent(rentID);
    }

    @GetMapping("/manager/getRentByID")
    public ResponseEntity<RentDTO> getRentByRoomNumber(@RequestParam ObjectId rentID) {
        RentDTO rentDTO = rentService.getRentByID(rentID);
        return ResponseEntity.ok(rentDTO);
    }
}
