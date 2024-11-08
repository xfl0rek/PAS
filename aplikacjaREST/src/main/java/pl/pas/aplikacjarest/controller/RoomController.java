package pl.pas.aplikacjarest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.pas.aplikacjarest.model.Room;

@Controller
@RequestMapping("/room")
public class RoomController {

    public RoomController() {}

    @GetMapping("/{roomNumber}")
    public String getRoom(@PathVariable long roomNumber, Model model) {
        Room room = new Room(roomNumber, 1000, 2);
        model.addAttribute("room", room);
        return "room";
    }
}
