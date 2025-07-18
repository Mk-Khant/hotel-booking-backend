package org.example.hotelmanagementbackend;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementbackend.dao.RoleDao;
import org.example.hotelmanagementbackend.dao.RoomDao;
import org.example.hotelmanagementbackend.dao.UserDao;
import org.example.hotelmanagementbackend.entity.Role;
import org.example.hotelmanagementbackend.entity.Room;
import org.example.hotelmanagementbackend.entity.User;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@RequiredArgsConstructor
public class HotelManagementBackendApplication {
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final RoomDao roomDao;
    private final PasswordEncoder passwordEncoder;

//    @Bean
//    @Transactional
//    //@Profile("dev1")
//    public ApplicationRunner runner() {
//        return r -> {
//            Room r1 = new Room("Single Room", 70.00, "Cozy room ideal for solo travelers", "single.jpg");
//            Room r2 = new Room("Double Room", 110.00, "Comfortable room with one double bed", "double.jpg");
//            Room r3 = new Room("Deluxe Single Room", 90.00, "Upgraded single room with workspace", "deluxe-single.jpg");
//            Room r4 = new Room("Deluxe Double Room", 130.00, "Spacious double room with balcony", "deluxe-double.jpg");
//            Room r5 = new Room("Executive Double Room", 150.00, "Double room with city view and desk", "executive-double.jpg");
//            Room r6 = new Room("Family Room", 180.00, "Room with extra beds for family stay", "family-room.jpg");
//            Room r7 = new Room("Honeymoon Suite", 220.00, "Private romantic suite for couples", "honeymoon.jpg");
//            Room r8 = new Room("Standard Double Room", 100.00, "Simple double room with all basics", "standard-double.jpg");
//            Room r9 = new Room("Penthouse Suite", 480.00, "Top floor suite with panoramic city view", "penthouse.jpg");
//            Room r10 = new Room("Presidential Suite", 500.00, "Luxurious suite with VIP amenities", "presidential.jpg");
//
//            roomDao.save(r1);
//            roomDao.save(r2);
//            roomDao.save(r3);
//            roomDao.save(r4);
//            roomDao.save(r5);
//            roomDao.save(r6);
//            roomDao.save(r7);
//            roomDao.save(r8);
//            roomDao.save(r9);
//            roomDao.save(r10);
//
//
//        };
//    }

    @Bean
    @Transactional
   // @Profile("dev")
    public ApplicationRunner init() {
        return r -> {
            Role customerRole = new Role();
            customerRole.setRoleName("ROLE_CUSTOMER");
            Role adminRole = new Role();
            adminRole.setRoleName("ROLE_ADMIN");

            User customer = new User("mary",passwordEncoder.encode("12345"),"mary@gmail.com","22-11-44");
            customer.getRoles().add(customerRole);
            User admin = new User("admin",passwordEncoder.encode("admin@123"),"admin@gmail.com","22-11-44");
            admin.getRoles().add(adminRole);

            roleDao.save(customerRole);
            roleDao.save(adminRole);

            userDao.save(customer);
            userDao.save(admin);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(HotelManagementBackendApplication.class, args);
    }

}
