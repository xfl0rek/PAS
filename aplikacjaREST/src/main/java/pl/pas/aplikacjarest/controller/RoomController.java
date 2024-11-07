package pl.pas.aplikacjarest.controller;

import org.springframework.web.bind.annotation.*;
import pl.pas.aplikacjarest.model.Room;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    public RoomController() {}

    @GetMapping
    public String room(@RequestParam long roomNumber,
                       @RequestParam int basePrice,
                       @RequestParam int roomCapacity) {
        return new Room(roomNumber, basePrice, roomCapacity).toString();
    }
}
