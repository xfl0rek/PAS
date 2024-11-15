package pl.pas.aplikacjarest.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pas.aplikacjarest.dto.RoomDTO;
import pl.pas.aplikacjarest.service.RoomService;

import java.util.List;

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

    @GetMapping("/manager/getRoomByID")
    public ResponseEntity<RoomDTO> getRoomByID(@RequestParam ObjectId roomID) {
        RoomDTO roomDTO = roomService.getRoomByID(roomID);
        return ResponseEntity.ok(roomDTO);
    }

    @PostMapping("/manager/updateRoom")
    public void updateRoom(@RequestBody RoomDTO updatedRoom) {
        roomService.updateRoom(updatedRoom);
    }

    @PostMapping("/admin/deleteRoom")
    public void deleteRoom(@RequestParam int roomNumber) {
        roomService.deleteRoom(roomNumber);
    }

    @GetMapping("/manager/getRoomsByRoomCapacity")
    public ResponseEntity<List<RoomDTO>> getRoomsByRoomCapacity(@RequestParam int roomCapacity) {
        List<RoomDTO> roomDTOs = roomService.getRoomsByRoomCapacity(roomCapacity);
        return ResponseEntity.ok(roomDTOs);
    }
}
