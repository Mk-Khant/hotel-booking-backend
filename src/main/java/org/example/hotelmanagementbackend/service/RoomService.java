package org.example.hotelmanagementbackend.service;

import org.example.hotelmanagementbackend.dao.BookingDao;
import org.example.hotelmanagementbackend.dao.UserDao;
import org.example.hotelmanagementbackend.dto.RoomDetailsInfo;
import org.example.hotelmanagementbackend.entity.Booking;
import org.example.hotelmanagementbackend.entity.Room;
import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementbackend.dao.RoomDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
    private Integer id;
    private String roomName;
    @Column(columnDefinition = "text")
    private String roomDescription;
    private double fees;
    @Lob
    private byte[] imageBase64;
 */

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomDao roomDao;
    private final UserDao userDao;
    private final BookingDao bookingDao;

    @Transactional
    public String createRoom(
            String roomName,
            String roomDescription,
            double fees,
            MultipartFile imageBase64
            // String adminName
    ) throws IOException {
        Room room = new Room(roomName, roomDescription, fees, imageBase64.getBytes());
        Room saveRoom = roomDao.save(room);
        return "%s room successfully created! ".formatted(saveRoom.getRoomName());
    }

    public List<RoomDetailsInfo> findAllRoomDetailsInfo() {
        return roomDao.findAllRoomsDetailsInfo();
    }

    public Room findRoomById(Integer id) {
        return roomDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found!"));
    }

    @Transactional
    public Room updateRoom(Integer roomId, String roomName, String roomDescription, double fees, MultipartFile imageBase64) throws IOException {
        Room room = roomDao.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found!"));
        room.setRoomName(roomName);
        room.setRoomDescription(roomDescription);
        room.setFees(fees);
        if (imageBase64 != null && !imageBase64.isEmpty()) {
            room.setImageBase64(imageBase64.getBytes());
        }
        return roomDao.save(room);
    }

    public void deleteRoomById(Integer id) {
        roomDao.deleteById(id);
    }

    public List<Room> getRoomsByCriteria(String roomType, String checkInDate, String checkOutDate) {
        LocalDate checkIn = parseDate(checkInDate);
        LocalDate checkOut = parseDate(checkOutDate);

        List<Room> rooms = roomDao.findAll();

        if (roomType != null && !roomType.isEmpty() && !roomType.equals("Room Type")) {
            rooms = rooms.stream()
                    .filter(room -> room.getRoomName().equalsIgnoreCase(roomType))
                    .collect(Collectors.toList());
        }

        if (checkIn != null && checkOut != null && !checkIn.isAfter(checkOut)) {
            rooms = rooms.stream()
                    .filter(room -> isRoomAvailable(room, checkIn, checkOut))
                    .collect(Collectors.toList());
        }

        return rooms;
    }

    public List<Room> searchRoomsByName(String roomName) {
        return roomDao.findAll().stream()
                .filter(room -> room.getRoomName().toLowerCase().contains(roomName.toLowerCase()))
                .collect(Collectors.toList());
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isRoomAvailable(Room room, LocalDate checkIn, LocalDate checkOut) {
        Optional<Booking> bookings = bookingDao.findByRoomId(room.getId());
        return bookings.stream().noneMatch(booking ->
                !(checkOut.isBefore(booking.getCheckInDate()) || checkIn.isAfter(booking.getCheckOutDate()))
        );
    }
}