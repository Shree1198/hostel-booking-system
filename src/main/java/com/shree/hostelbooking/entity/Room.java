package com.shree.hostelbooking.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //
    private String roomNumber;
    // different type for now AC / non AC
    private String type;
//    private boolean isAvailable;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Bed> beds;

}
