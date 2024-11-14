package pl.pas.aplikacjarest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pas.aplikacjarest.dto.RoomDTO;
import pl.pas.aplikacjarest.service.RoomService;

@RestController
public class RoomController {
    RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/manager/createRoom")
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO createdRoom) {
        RoomDTO roomDTO = roomService.createRoom(createdRoom);
        return ResponseEntity.ok(roomDTO);
    }

    @PostMapping("/admin/deleteRoom")
    public void deleteRoom(@RequestParam int roomNumber) {
        roomService.deleteRoom(roomNumber);
    }
}
