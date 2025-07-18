package org.example.hotelmanagementbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementbackend.dto.RoomDetailsInfo;
import org.example.hotelmanagementbackend.entity.Room;
import org.example.hotelmanagementbackend.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<String> createRoom(
            @RequestParam("roomName") String roomName,
            @RequestParam("roomDescription") String roomDescription,
            @RequestParam("fees") double fees,
            @RequestParam(value = "imageBase64", required = false) MultipartFile imageBase64
    ) throws IOException {
        System.out.println("Room Name:" + roomName);
        String returnString = roomService.createRoom(roomName, roomDescription, fees, imageBase64);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnString);
    }

    @GetMapping
    public List<RoomDetailsInfo> getAllRoomsDetailsInfo() {
        return roomService.findAllRoomDetailsInfo();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDetailsInfo> getRoomById(@PathVariable Integer id) {
        Room room = roomService.findRoomById(id);
        RoomDetailsInfo roomDto = new RoomDetailsInfo(
                room.getId(),
                room.getRoomName(),
                room.getRoomDescription(),
                room.getFees(),
                room.getImageBase64()
        );
        return ResponseEntity.ok(roomDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(
            @PathVariable Integer id,
            @RequestParam("roomName") String roomName,
            @RequestParam("roomDescription") String roomDescription,
            @RequestParam("fees") double fees,
            @RequestParam(value = "imageBase64", required = false) MultipartFile imageBase64
    ) throws IOException {
        Room updatedRoom = roomService.updateRoom(id, roomName, roomDescription, fees, imageBase64);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Integer id) {
        roomService.deleteRoomById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<RoomDetailsInfo>> searchRoomsByName(@RequestParam String roomName) {
        List<Room> rooms = roomService.searchRoomsByName(roomName);
        List<RoomDetailsInfo> roomDetails = rooms.stream()
                .map(room -> new RoomDetailsInfo(
                        room.getId(),
                        room.getRoomName(),
                        room.getRoomDescription(),
                        room.getFees(),
                        room.getImageBase64()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(roomDetails);
    }
}