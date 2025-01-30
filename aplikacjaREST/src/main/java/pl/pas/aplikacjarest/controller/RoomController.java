package pl.pas.aplikacjarest.controller;

import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.pas.aplikacjarest.dto.RoomDTO;
import pl.pas.aplikacjarest.exception.RoomNotFoundException;
import pl.pas.aplikacjarest.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<RoomDTO> createRoom(@Valid @RequestBody RoomDTO createdRoom) {
        RoomDTO roomDTO = roomService.createRoom(createdRoom);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RoomDTO> getRoomByID(@PathVariable String id) {
        ObjectId roomID;
        try {
            roomID = new ObjectId(id);
        } catch (Exception e) {
            throw new RoomNotFoundException("Room not found");
        }
        RoomDTO roomDTO = roomService.getRoomByID(roomID);
        return ResponseEntity.ok(roomDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<Void> updateRoom(@PathVariable String id, @Valid @RequestBody RoomDTO updatedRoom) {
        ObjectId roomID;
        try {
            roomID = new ObjectId(id);
        } catch (Exception e) {
            throw new RoomNotFoundException("Room not found");
        }
        roomService.updateRoom(roomID, updatedRoom);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<Void> deleteRoom(@PathVariable String id) {
        ObjectId roomID;
        try {
            roomID = new ObjectId(id);
        } catch (Exception e) {
            throw new RoomNotFoundException("Room not found");
        }
        roomService.deleteRoom(roomID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getRoomsByRoomCapacity")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<RoomDTO>> getRoomsByRoomCapacity(@RequestParam int roomCapacity) {
        List<RoomDTO> roomDTOs = roomService.getRoomsByRoomCapacity(roomCapacity);
        return ResponseEntity.ok(roomDTOs);
    }

    @GetMapping("/getRoomsByBasePrice")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<RoomDTO>> getRoomsByBasePrice(@RequestParam int basePrice) {
        List<RoomDTO> roomDTOs = roomService.getRoomsByBasePrice(basePrice);
        return ResponseEntity.ok(roomDTOs);
    }

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        List<RoomDTO> roomDTOs = roomService.findAll();
        return ResponseEntity.ok(roomDTOs);
    }
}
