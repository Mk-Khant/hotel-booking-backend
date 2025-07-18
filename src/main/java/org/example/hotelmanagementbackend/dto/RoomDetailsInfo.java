package org.example.hotelmanagementbackend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Base64;

/*
export type RoomDto ={
    roomId?:number;
    roomName:string;
    roomDescription:string;
    fees:number;
    imageBase64:string;
}
 */

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RoomDetailsInfo{
    int roomId;
    String roomName;
    String roomDescription;
    double fees;
    String imageBase64;

    public RoomDetailsInfo(int roomId,
                           String roomName,
                           String roomDescription,
                           double fees,
                           byte[] imageBase64) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.fees = fees;
        this.imageBase64 = imageBase64 != null ? Base64.getEncoder().encodeToString(imageBase64):null;
    }
}
