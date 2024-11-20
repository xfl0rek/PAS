package pl.pas.aplikacjarest.controller;

import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<RoomDTO> createRoom(@Valid @RequestBody RoomDTO createdRoom) {
        RoomDTO roomDTO = roomService.createRoom(createdRoom);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomDTO);
    }

    @GetMapping("/manager/{id}")
    public ResponseEntity<RoomDTO> getRoomByID(@PathVariable String id) {
        ObjectId roomID = new ObjectId(id);
        RoomDTO roomDTO = roomService.getRoomByID(roomID);
        return ResponseEntity.ok(roomDTO);
    }

    @PostMapping("/manager/{id}")
    public ResponseEntity<Void> updateRoom(@PathVariable String id, @Valid @RequestBody RoomDTO updatedRoom) {
        ObjectId roomID = new ObjectId(id);
        roomService.updateRoom(roomID, updatedRoom);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/manager/{id}")
//    public ResponseEntity<RoomDTO> getRoomByID(@PathVariable String id) {
//        try {
//            ObjectId roomID = new ObjectId(id);
//            RoomDTO roomDTO = roomService.getRoomByID(roomID);
//            return ResponseEntity.ok(roomDTO);
//        } catch (IllegalArgumentException e) {
//            // Handle invalid ObjectId format
//            return ResponseEntity.badRequest().build(); // Returns 400 Bad Request
//        } catch (RoomNotFoundException e) {
//            // Custom exception for when the room is not found
//            return ResponseEntity.notFound().build(); // Returns 404 Not Found
//        }
//    }
//
//    @PostMapping("/manager/{id}")
//    public ResponseEntity<Void> updateRoom(@PathVariable String id, @RequestBody RoomDTO updatedRoom) {
//        try {
//            ObjectId roomID = new ObjectId(id);
//            roomService.updateRoom(roomID, updatedRoom);
//            return ResponseEntity.noContent().build(); // Returns 204 No Content
//        } catch (IllegalArgumentException e) {
//            // Handle invalid ObjectId format
//            return ResponseEntity.badRequest().build(); // Returns 400 Bad Request
//        } catch (RoomNotFoundException e) {
//            // Custom exception for when the room to update is not found
//            return ResponseEntity.notFound().build(); // Returns 404 Not Found
//        }
//    }


    @PostMapping("/admin/deleteRoom/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable String id) {
        ObjectId roomID = new ObjectId(id);
        roomService.deleteRoom(roomID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/manager/getRoomsByRoomCapacity")
    public ResponseEntity<List<RoomDTO>> getRoomsByRoomCapacity(@RequestParam int roomCapacity) {
        List<RoomDTO> roomDTOs = roomService.getRoomsByRoomCapacity(roomCapacity);
        return ResponseEntity.ok(roomDTOs);
    }

    @GetMapping("/manager/getRoomsByBasePrice")
    public ResponseEntity<List<RoomDTO>> getRoomsByBasePrice(@RequestParam int basePrice) {
        List<RoomDTO> roomDTOs = roomService.getRoomsByBasePrice(basePrice);
        return ResponseEntity.ok(roomDTOs);
    }

    @GetMapping("/manager/getAllRooms")
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        List<RoomDTO> roomDTOs = roomService.findAll();
        return ResponseEntity.ok(roomDTOs);
    }
}
