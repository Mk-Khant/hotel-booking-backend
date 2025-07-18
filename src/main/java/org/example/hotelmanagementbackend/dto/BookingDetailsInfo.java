package org.example.hotelmanagementbackend.dto;
/*
bookingId?:number;
    customerName:string;
    roomName:string;
    checkInDate:Date;
    checkOutDate:Date;
    imageBase64:string;
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Base64;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BookingDetailsInfo {
    int bookingId;
    String customerName;
    String roomName;
    LocalDate checkInDate;
    LocalDate checkOutDate;
    String imageBase64;
    String status;

    public BookingDetailsInfo(int bookingId,
                              String customerName,
                              String roomName,
                              LocalDate checkInDate,
                              LocalDate checkOutDate,
                              byte[] imageBase64,
                              String status) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomName = roomName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.imageBase64 = imageBase64 != null ? Base64.getEncoder().encodeToString(imageBase64) : null;
        this.status = status;
    }
}