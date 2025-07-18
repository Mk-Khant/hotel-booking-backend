package org.example.hotelmanagementbackend.service;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementbackend.dao.BookingDao;
import org.example.hotelmanagementbackend.dao.RoomDao;
import org.example.hotelmanagementbackend.dto.BookingDetailsInfo;
import org.example.hotelmanagementbackend.entity.Booking;
import org.example.hotelmanagementbackend.entity.Room;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * @Id
 *     @GeneratedValue(strategy = GenerationType.IDENTITY)
 *     private Integer id;
 *     private String customerName;
 *     private String roomName;
 *     private LocalDate checkInDate;
 *     private LocalDate checkOutDate;
 *     @Lob
 *     private byte[] imageBase64;
 */

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingDao bookingDao;
    private final RoomDao roomDao;

    @Transactional
    public String createBooking(
            String customerName,
            String roomName,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            MultipartFile imageBase64
    ) throws IOException {
        Room room = getRoom(roomName);
        Booking booking = new Booking(
                customerName,
                roomName,
                checkInDate,
                checkOutDate,
                imageBase64.getBytes(),
                "Pending" // Default status
        );
        room.addBooking(booking);
        return "%s booking successfully created".formatted(booking.getCustomerName(), roomName);
    }

    public List<BookingDetailsInfo> findAllBookingDetailsInfo(){
        return bookingDao.findAllBookingsDetailsInfo();
    }

    @Transactional
    public void updateBookingStatus(Integer id, String status) {
        Booking booking = bookingDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        booking.setStatus(status);
        bookingDao.save(booking);
    }

    public void deleteBookingById(Integer id){
        bookingDao.deleteById(id);
    }

    private Room getRoom(String roomName) {
        return roomDao.findByRoomName(roomName)
                .orElseThrow(() -> new RuntimeException(roomName));
    }
}
