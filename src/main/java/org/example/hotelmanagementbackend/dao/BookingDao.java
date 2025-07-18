package org.example.hotelmanagementbackend.dao;

import org.example.hotelmanagementbackend.dto.BookingDetailsInfo;
import org.example.hotelmanagementbackend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookingDao extends JpaRepository<Booking, Integer> {
    Optional<Booking> findByRoomId(int roomId);

    @Query("""
    select new org.example.hotelmanagementbackend.dto.BookingDetailsInfo(
            b.id, b.customerName, b.roomName, b.checkInDate,
            b.checkOutDate, b.imageBase64, b.status
    )
    from Booking b 
""")
    List<BookingDetailsInfo> findAllBookingsDetailsInfo();
}
