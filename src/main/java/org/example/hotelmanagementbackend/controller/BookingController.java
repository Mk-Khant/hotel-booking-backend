package org.example.hotelmanagementbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementbackend.dto.BookingDetailsInfo;
import org.example.hotelmanagementbackend.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/*
customerName: e.target.customerName.value,
      roomName: e.target.roomName.value,
      checkInDate: e.target.checkInDate.value,
      checkOutDate: e.target.checkOutDate.value,
      imageBase64: e.target.image.files[0],
 */
@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<String> createBooking(
            @RequestParam("customerName")String customerName,
            @RequestParam("roomName")String roomName,
            @RequestParam("checkInDate")LocalDate checkInDate,
            @RequestParam("checkOutDate")LocalDate checkOutDate,
            @RequestParam(value = "imageBase64",required = false) MultipartFile imageBase64
            )throws IOException {
        System.out.println(customerName);
        String returnString = bookingService.createBooking(
              customerName,
              roomName,
              checkInDate,
              checkOutDate,
              imageBase64
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(returnString);
    }

    @GetMapping
    public List<BookingDetailsInfo> getAllBookingsDetailsInfo(){
        return bookingService.findAllBookingDetailsInfo();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateBookingStatus(@PathVariable Integer id, @RequestParam String status) {
        bookingService.updateBookingStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Integer id) {
        bookingService.deleteBookingById(id);
        return ResponseEntity.noContent().build();
    }
}
