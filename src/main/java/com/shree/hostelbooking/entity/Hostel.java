package com.shree.hostelbooking.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class Hostel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hostelId;

    private String name;
    private String emailId;

    @Embedded
    private Address address;

    private String contact;
    private int totalFloor;

    @OneToMany(mappedBy = "hostel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;
}

@Embeddable
@Data
class Address {
    private String street;
    private String city;
    private String state;
    private String pinCode;
}
