package pl.pas.aplikacjarest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.pas.aplikacjarest.model.Room;
import pl.pas.aplikacjarest.service.RoomService;

@Controller
@RequestMapping("/room")
public class RoomController {

    RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/{roomNumber}")
    public String getRoom(@PathVariable long roomNumber, Model model) {
        Room room = roomService.getRoom(roomNumber);
        model.addAttribute("room", room);
        return "room";
    }
}
