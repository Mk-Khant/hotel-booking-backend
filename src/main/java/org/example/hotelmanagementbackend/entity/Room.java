package org.example.hotelmanagementbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/*
formData.append("roomName", e.target.roomName.value);
    formData.append("roomDescription", e.target.roomDescription.value);
    formData.append("fees", e.target.fees.value);
    formData.append("imageBase64", e.target.image.files[0]);
 */

@Entity
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String roomName;
    @Column(columnDefinition = "text")
    private String roomDescription;
    private double fees;
    @Lob
    private byte[] imageBase64;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();


    public Room() {}

    public Room(String roomName, String roomDescription, double fees, byte[] imageBase64) {
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.fees = fees;
        this.imageBase64 = imageBase64;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
        booking.setRoom(this);
    }
}
