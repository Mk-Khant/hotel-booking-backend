package org.example.hotelmanagementbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String customerName;
    private String roomName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    @Lob
    private byte[] imageBase64;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "rooms_id", nullable = false)
    private Room room;

    private String status;

    public Booking(){}

    public Booking(String customerName, String roomName, LocalDate checkInDate, LocalDate checkOutDate, byte[] imageBase64, String status) {
        this.customerName = customerName;
        this.roomName = roomName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.imageBase64 = imageBase64;
        this.status = status;
    }
}
