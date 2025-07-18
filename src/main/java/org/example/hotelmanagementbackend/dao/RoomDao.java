package org.example.hotelmanagementbackend.dao;

import org.example.hotelmanagementbackend.dto.RoomDetailsInfo;
import org.example.hotelmanagementbackend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
/*
 public RoomDetailsInfo(int roomId,
                           String roomName,
                           String roomDescription,
                           double fees,
                           byte[] imageBase64)
 */
public interface RoomDao extends JpaRepository<Room, Integer> {
    Optional<Room> findByRoomName(String roomName);

    @Query("""
        select new org.example.hotelmanagementbackend.dto.RoomDetailsInfo(r.id,
            r.roomName,r.roomDescription,r.fees,
                r.imageBase64)
            from Room  r 
    """)
    List<RoomDetailsInfo> findAllRoomsDetailsInfo();
}
