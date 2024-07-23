package com.shree.hostelbooking.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    //
    private String roomNumber;
    // different type for now AC / non AC
    private String type;

    @ManyToOne
    @JoinColumn(name = "hostel_id")
    private Hostel hostel;

//    @OneToMany(cascade = CascadeType.ALL)
//    private List<Bed> beds;

}
